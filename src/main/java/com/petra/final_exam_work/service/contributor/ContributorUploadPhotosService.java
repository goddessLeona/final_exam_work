package com.petra.final_exam_work.service.contributor;

import com.petra.final_exam_work.dto.mapperDto.contributor.UploadPhotoContentMapper;
import com.petra.final_exam_work.dto.requestDto.contributor.UploadPhotoContentRequest;
import com.petra.final_exam_work.dto.responseDto.contributor.ContributorUploadPhotos.UploadContentResponse;
import com.petra.final_exam_work.entity.enums.ContentStatus;
import com.petra.final_exam_work.entity.enums.ContentType;
import com.petra.final_exam_work.entity.enums.ContributorStatus;
import com.petra.final_exam_work.entity.junktionTables.photoAlbumPhoto.PhotoAlbumPhoto;
import com.petra.final_exam_work.entity.photo.Photo;
import com.petra.final_exam_work.entity.photo.PhotoAlbum;
import com.petra.final_exam_work.entity.user.User;
import com.petra.final_exam_work.repository.PhotoAlbumPhotoRepository;
import com.petra.final_exam_work.repository.PhotoAlbumRepository;
import com.petra.final_exam_work.repository.PhotoRepository;
import com.petra.final_exam_work.repository.UserRepository;
import com.petra.final_exam_work.security.CustomUserDetails;
import com.petra.final_exam_work.exception.ApiException;
import com.petra.final_exam_work.service.FileStorageService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ContributorUploadPhotosService {

    private final UploadPhotoContentMapper uploadPhotoContentMapper;
    private final UserRepository userRepository;
    private final PhotoAlbumRepository photoAlbumRepository;
    private final PhotoRepository photoRepository;
    private final PhotoAlbumPhotoRepository photoAlbumPhotoRepository;
    private final FileStorageService fileStorageService;

    public ContributorUploadPhotosService(UploadPhotoContentMapper uploadPhotoContentMapper, UserRepository userRepository, PhotoAlbumRepository photoAlbumRepository, PhotoRepository photoRepository, PhotoAlbumPhotoRepository photoAlbumPhotoRepository, FileStorageService fileStorageService) {
        this.uploadPhotoContentMapper = uploadPhotoContentMapper;
        this.userRepository = userRepository;
        this.photoAlbumRepository = photoAlbumRepository;
        this.photoRepository = photoRepository;
        this.photoAlbumPhotoRepository = photoAlbumPhotoRepository;
        this.fileStorageService = fileStorageService;
    }

    //######### CONTRIBUTOR POST-CONTENT ########
    @Transactional
    public UploadContentResponse uploadContent (
            CustomUserDetails userDetails,
            UploadPhotoContentRequest request) throws IOException {

        User user = getValidatedContributor(userDetails);

        ContentType type = resolveContentType(request);

        validateUploadRequest(request, type);

        validateAlbumName(request, user);

        switch (type) {
            case PHOTO -> {
                return uploadPhotoAlbum(user, request);
            }

            case VIDEO -> {
                //return uploadVideoAlbum(user, request);
            }
        }

        throw new IllegalStateException("unsupported content type");

    }

    // ---------------UPLOAD PHOTO ALBUM--------------
    private UploadContentResponse uploadPhotoAlbum(
            User user,
            UploadPhotoContentRequest request
    ) throws IOException {

        // upload and save album
        PhotoAlbum photoAlbum = createPhotoAlbum(user, request);

        // save photos
        List<Photo> savedPhotos = savePhotos( user, request, photoAlbum);

        // create links between db and where file is stored
        createPhotoAlbumLinks(photoAlbum, savedPhotos);



        return uploadPhotoContentMapper.toResponse(
                photoAlbum,
                savedPhotos
        );

    }

    // Create a photo album and saving it
    private PhotoAlbum createPhotoAlbum(
            User user,
            UploadPhotoContentRequest request
    ) {
        PhotoAlbum photoAlbum = new PhotoAlbum();

        Instant now = Instant.now();
        Instant publishedAt = request.getPublishedAt();

        if (publishedAt != null &&
                publishedAt.isBefore(now.minusSeconds(60))
        ){
            throw new ApiException(
                    "Published date can not be in the past",
                    HttpStatus.BAD_REQUEST
            );
        }

        photoAlbum.setPhotoAlbumName(request.getPhotoAlbumName());
        photoAlbum.setDescription(request.getDescription());
        photoAlbum.setContentType(ContentType.PHOTO);
        photoAlbum.setOwnedByUser(user);
        photoAlbum.setRulesVerified(false);

        photoAlbum.setPublishedAt(publishedAt);

        if (publishedAt == null) {
            photoAlbum.setContentStatus(ContentStatus.DRAFT);
        } else if (publishedAt.isAfter(now)) {
            photoAlbum.setContentStatus(ContentStatus.SCHEDULED);
        } else {
            photoAlbum.setContentStatus(ContentStatus.PUBLISHED);
        }

        photoAlbumRepository.save(photoAlbum);

        return photoAlbum;
    }

    // save photos
    private List<Photo> savePhotos(
            User user,
            UploadPhotoContentRequest request,
            PhotoAlbum photoAlbum) throws IOException
    {

        List<Photo> photosToSave = new ArrayList<>();

        for (MultipartFile file : request.getPhotos()) {

            String path = handleImageUpload(
                    file,
                    user.getPublicUuid(),
                    "albums/photo/" + photoAlbum.getPublicUuid(),
                    "photo"
            );

            BufferedImage image;

            try {
                image = ImageIO.read(file.getInputStream());
            } catch (IOException ex) {
                throw new ApiException(
                        "Unable to read uploaded image",
                        HttpStatus.BAD_REQUEST
                );
            }

            Photo photo = new Photo();

            photo.setUploadedByUser(user);
            photo.setThumbnailPath(path);
            photo.setMediumPath(path);
            photo.setLargePath(path);
            photo.setFileName(file.getOriginalFilename());
            photo.setHeight(image.getHeight());
            photo.setWidth(image.getWidth());
            photo.setMimeType(file.getContentType());
            photo.setSizeBytes(file.getSize());

            photosToSave.add(photo);
        }

        List<Photo> savedPhotos = photoRepository.saveAll(photosToSave);

        if (!savedPhotos.isEmpty()) {
            Integer coverPhotoIndex = request.getCoverPhotoIndex();

            if(
                    coverPhotoIndex != null &&
                            coverPhotoIndex >= 0 &&
                            coverPhotoIndex < savedPhotos.size()
            ) {
                photoAlbum.setCoverPhoto(savedPhotos.get(coverPhotoIndex));
            } else {
                photoAlbum.setCoverPhoto(savedPhotos.get(0));
            }
        }

        return savedPhotos;
    }

    // links
    private void createPhotoAlbumLinks(
            PhotoAlbum photoAlbum,
            List<Photo> savedPhotos
    ){
        List<PhotoAlbumPhoto> links = new ArrayList<>();

        int position = 0;

        for(Photo photo : savedPhotos) {

            PhotoAlbumPhoto link = new PhotoAlbumPhoto();

            link.setPhotoAlbum(photoAlbum);
            link.setPhoto(photo);
            link.setPosition(position++);

            links.add(link);

        }

        photoAlbumPhotoRepository.saveAll(links);
    }

    //--------------------UPLOAD VIDEO ALBUM ---------------------------
    private void uploadVideoAlbum(
            User user,
            UploadPhotoContentRequest request
    ) {

    }


    // ---------------- Private helper ----------------
    private String handleImageUpload(
            MultipartFile file,
            UUID userUuid,
            String category,
            String filePrefix
    ) {
        if (file.isEmpty()) {
            throw new ApiException("File is empty", HttpStatus.BAD_REQUEST);
        }

        if (file.getContentType() == null || !file.getContentType().startsWith("image/")) {
            throw new ApiException("Only images allowed", HttpStatus.BAD_REQUEST);
        }

        fileStorageService.validateImage(file);

        return fileStorageService.save(file, userUuid, category, filePrefix);
    }

    // ----------------VALIDATIONS-------------------

    //validate the contributors access rights
    private User getValidatedContributor (CustomUserDetails userDetails) {
        UUID publicUuid = userDetails.getPublicUuid();
        User user = userRepository.findByPublicUuid(publicUuid)
                .orElseThrow(() -> new ApiException("User was not found", HttpStatus.NOT_FOUND));


        if (user.getContributorStatus() != ContributorStatus.APPROVED) {
            throw new ApiException(
                    "You are not approved to upload content",
                    HttpStatus.FORBIDDEN
            );
        }
        return user;
    }

    // validate and split the how to validate different depending on photo or video
    private void validateUploadRequest(
            UploadPhotoContentRequest request,
            ContentType type
    ) {
        if (type == ContentType.PHOTO) {
            validatePhotoAlbumUpload(request);
        } else {
            validateVideoAlbumUpload(request);
        }
    }

    // validate how uploaded photos
    private void validatePhotoAlbumUpload(
            UploadPhotoContentRequest request
    ) {
        if(request.getPhotos() == null || request.getPhotos().isEmpty() || request.getPhotos().size() < 7) {
            throw new ApiException(
                    "You have to have minimum 7 photo to create a photo album",
                    HttpStatus.BAD_REQUEST
            );
        }

        if (request.getPhotos().size() > 30) {
            throw new ApiException(
                    "Can max be 30 photos in a photo album",
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    //Validation for uploaded videos
    private void validateVideoAlbumUpload(
            UploadPhotoContentRequest request
    ) {
        if (request.getPhotos() == null || request.getPhotos().isEmpty()) {
            throw new ApiException("Video file is required", HttpStatus.BAD_REQUEST);
        }

        if (request.getPhotos() != null && request.getPhotos().size()>1) {
            throw new ApiException("Only one video allowed per upload", HttpStatus.BAD_REQUEST);
        }
    }

    //Validate album name
    private void validateAlbumName(
            UploadPhotoContentRequest request,
            User user

    ) {

        boolean exists = photoAlbumRepository
                .existsByOwnedByUserAndPhotoAlbumName(
                        user,
                        request.getPhotoAlbumName()
                );

        if (exists) {
            throw new ApiException(
                    "You already have an album with this name",
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    // method checking what type the content is
    private ContentType resolveContentType(
            UploadPhotoContentRequest request
    ) {
        return request.getContentType() !=null
                ? request.getContentType()
                : ContentType.PHOTO;
    }
}
