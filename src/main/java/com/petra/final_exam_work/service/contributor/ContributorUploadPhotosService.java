package com.petra.final_exam_work.service.contributor;

import com.petra.final_exam_work.dto.mapperDto.contributor.UploadPhotoContentMapper;
import com.petra.final_exam_work.dto.requestDto.contributor.UploadPhotoContentRequest;
import com.petra.final_exam_work.entity.enums.ContentStatus;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ContributorUploadPhotosService {

    private UploadPhotoContentMapper uploadPhotoContentMapper;
    private UserRepository userRepository;
    private PhotoAlbumRepository photoAlbumRepository;
    private PhotoRepository photoRepository;
    private PhotoAlbumPhotoRepository photoAlbumPhotoRepository;

    public ContributorUploadPhotosService(UploadPhotoContentMapper uploadPhotoContentMapper, UserRepository userRepository, PhotoAlbumRepository photoAlbumRepository, PhotoRepository photoRepository, PhotoAlbumPhotoRepository photoAlbumPhotoRepository) {
        this.uploadPhotoContentMapper = uploadPhotoContentMapper;
        this.userRepository = userRepository;
        this.photoAlbumRepository = photoAlbumRepository;
        this.photoRepository = photoRepository;
        this.photoAlbumPhotoRepository = photoAlbumPhotoRepository;
    }

    //######### CONTRIBUTOR POST-PHOTOS ########

    @Transactional
    public void uploadPhotos (CustomUserDetails userDetails, UploadPhotoContentRequest request) {

        UUID publicUuid = userDetails.getPublicUuid();
        User user = userRepository.findByPublicUuid(publicUuid)
                .orElseThrow(() -> new ApiException("User was not found", HttpStatus.NOT_FOUND));


        if (user.getContributorStatus() != ContributorStatus.APPROVED) {
            throw new ApiException(
                    "You are not approved to upload content",
                    HttpStatus.FORBIDDEN
            );
        }

        PhotoAlbum photoAlbum = new PhotoAlbum();

        photoAlbum.setPhotoAlbumName(request.getPhotoAlbumName());
        photoAlbum.setDescription(request.getDescription());
        photoAlbum.setPublishedDate(request.getPublishedAt());
        photoAlbum.setContentStatus(
                request.getContentStatus() != null
                    ? request.getContentStatus()
                    : ContentStatus.DRAFT
        );
        photoAlbum.setOwnedByUser(user);
        photoAlbum.setRulesVerified(false);

        photoAlbumRepository.save(photoAlbum);

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

        List<Photo> savedPhotos = new ArrayList<>();

        for (MultipartFile file : request.getPhotos()) {

            String path = handleImageUpload(
                    file,
                    user.getPublicUuid(),
                    "albums/" + photoAlbum.getPublicUuid(),
                    "photo"
            );

            Photo photo = new Photo();

            photo.setUploadedByUser(user);
            photo.setPhotoFilePath(path);
            photo.setFileName(file.getOriginalFilename());
            photo.setMimeType(file.getContentType());
            photo.setSizeBytes(file.getSize());

            photoRepository.save(photo);
            savedPhotos.add(photo);
        }

        int position = 0;

        for(Photo photo : savedPhotos) {
            PhotoAlbumPhoto link = new PhotoAlbumPhoto();

            link.setPhotoAlbum(photoAlbum);
            link.setPhoto(photo);
            link.setPosition(position++);

            photoAlbumPhotoRepository.save(link);

        }
    }
}
