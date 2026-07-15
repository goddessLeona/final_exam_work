package com.petra.final_exam_work.service.contributor;

import com.petra.final_exam_work.dto.mapperDto.contributor.UploadPhotoContentMapper;
import com.petra.final_exam_work.dto.requestDto.contributor.UploadPhotoContentRequest;
import com.petra.final_exam_work.dto.responseDto.contributor.ContributorUploadPhotos.UploadContentResponse;
import com.petra.final_exam_work.dto.serviceDto.upload.FailedUpload;
import com.petra.final_exam_work.dto.serviceDto.upload.UploadResult;
import com.petra.final_exam_work.dto.serviceDto.upload.UploadedPhoto;
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
import com.petra.final_exam_work.service.ImageResizeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ContributorUploadContentService {

    private final UploadPhotoContentMapper uploadPhotoContentMapper;
    private final UserRepository userRepository;
    private final PhotoAlbumRepository photoAlbumRepository;
    private final PhotoRepository photoRepository;
    private final PhotoAlbumPhotoRepository photoAlbumPhotoRepository;
    private final FileStorageService fileStorageService;
    private final ImageResizeService imageResizeService;

    public ContributorUploadContentService(UploadPhotoContentMapper uploadPhotoContentMapper, UserRepository userRepository, PhotoAlbumRepository photoAlbumRepository, PhotoRepository photoRepository, PhotoAlbumPhotoRepository photoAlbumPhotoRepository, FileStorageService fileStorageService, ImageResizeService imageResizeService) {
        this.uploadPhotoContentMapper = uploadPhotoContentMapper;
        this.userRepository = userRepository;
        this.photoAlbumRepository = photoAlbumRepository;
        this.photoRepository = photoRepository;
        this.photoAlbumPhotoRepository = photoAlbumPhotoRepository;
        this.fileStorageService = fileStorageService;
        this.imageResizeService = imageResizeService;
    }

    //######### CONTRIBUTOR POST-CONTENT ########
    @Transactional
    public UploadContentResponse uploadContent (
            CustomUserDetails userDetails,
            UploadPhotoContentRequest request) {

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
    ) {

        // upload and save album temporary
        PhotoAlbum photoAlbum = createPhotoAlbum(user, request);

        // upload result
        UploadResult uploadResult = uploadPhotos(user, photoAlbum, request);
        if (uploadResult.successCount() < 7) {

            cleanupTemporaryUploads(uploadResult);

            throw new ApiException(
                    "You have to have minimum 7 photos to create a photo album. One or more files could not be uploaded.",
                    HttpStatus.BAD_REQUEST
            );
        }

        //resize photos
        resizePhoto(uploadResult);

        //move uploaded photos to permanent folder if all good
        moveUploadedPhotosToPermanent(uploadResult, user, photoAlbum);

        // save photos
        List<Photo> savedPhotos = savePhotos( user, photoAlbum, uploadResult, request);

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

    //resize photos
    private void resizePhoto(UploadResult uploadResult) {

        for (UploadedPhoto uploaded : uploadResult.getUploaded()) {

            //create thumbnail
            String thumbnailPath = imageResizeService.resizeAndSave(
                    uploaded.getLargePath(),
                    300,
                    "_thumb"
            );

            //create medium
            String mediumPath =
                    imageResizeService.resizeAndSave(
                            uploaded.getLargePath(),
                            800,
                            "_medium"
                    );

            //create Large
            String largePath =
                    imageResizeService.resizeAndSave(
                            uploaded.getLargePath(),
                            1200,
                            "_large"
                    );

            //update the UploadedPhoto paths
            uploaded.setThumbnailPath(thumbnailPath);
            uploaded.setMediumPath(mediumPath);
            uploaded.setLargePath(largePath);
        }

    }

    // save photos
    private List<Photo> savePhotos(
            User user,
            PhotoAlbum photoAlbum,
            UploadResult uploadResult,
            UploadPhotoContentRequest request
            )
    {

        List<Photo> photosToSave = new ArrayList<>();


        for (UploadedPhoto uploaded : uploadResult.getUploaded()) {

            Photo photo = new Photo();

            photo.setUploadedByUser(user);
            photo.setThumbnailPath(uploaded.getThumbnailPath());
            photo.setMediumPath(uploaded.getMediumPath());
            photo.setLargePath(uploaded.getLargePath());
            photo.setFileName(uploaded.getFileName());
            photo.setHeight(uploaded.getHeight());
            photo.setWidth(uploaded.getWidth());
            photo.setMimeType(uploaded.getMimeType());
            photo.setSizeBytes(uploaded.getSizeBytes());

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

    // upload photo
    private UploadResult uploadPhotos(
            User user,
            PhotoAlbum photoAlbum,
            UploadPhotoContentRequest request
    ) {
        UploadResult result = new UploadResult();

        for (MultipartFile file : request.getPhotos()) {

            try {
                String path = handleImageUpload(
                        file,
                        user.getPublicUuid(),
                        "albums/photo/" + photoAlbum.getPublicUuid(),
                        "photo"
                );

                BufferedImage image;
                image = ImageIO.read(file.getInputStream());

                if (image == null) {
                    throw new ApiException(
                            "Invalid or corrupted image",
                            HttpStatus.BAD_REQUEST
                    );
                }

                UploadedPhoto uploaded = new UploadedPhoto();

                uploaded.setThumbnailPath(path);
                uploaded.setMediumPath(path);
                uploaded.setLargePath(path);
                uploaded.setFileName(file.getOriginalFilename());
                uploaded.setHeight(image.getHeight());
                uploaded.setWidth(image.getWidth());
                uploaded.setMimeType(file.getContentType());
                uploaded.setSizeBytes(file.getSize());

                result.getUploaded().add(uploaded);

            } catch (Exception ex) {

                FailedUpload failed = new FailedUpload();

                failed.setFileName(file.getOriginalFilename());
                failed.setReason(ex.getMessage());

                result.getFailed().add(failed);
            }

        }
        return result;
    }

    //move temporary file
    private void moveUploadedPhotosToPermanent(
            UploadResult uploadResult,
            User user,
            PhotoAlbum photoAlbum
    ) {
        for(UploadedPhoto uploaded : uploadResult.getUploaded()) {

            String newPath = fileStorageService.moveToPermanent(
                    uploaded.getMediumPath(),
                    user.getPublicUuid(),
                    "albums/photo/" + photoAlbum.getPublicUuid()
            );

            uploaded.setMediumPath(newPath);
            uploaded.setLargePath(newPath);
            uploaded.setThumbnailPath(newPath);
        }
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

    //
    public void cleanupTemporaryUploads(
            UploadResult result
    ) {
        for (UploadedPhoto uploaded : result.getUploaded()) {
            fileStorageService.deleteTemporaryFiles(
                    uploaded.getMediumPath()
            );
        }
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

        return fileStorageService.storeTemporary(file, userUuid, category, filePrefix);
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
