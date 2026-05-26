package com.petra.final_exam_work.service.contributor;

import com.petra.final_exam_work.dto.mapperDto.contributor.editAlbum.EditCoverPhotoMapper;
import com.petra.final_exam_work.dto.mapperDto.contributor.editAlbum.EditTitleAndContributorMapper;
import com.petra.final_exam_work.dto.requestDto.contributor.editUploadedPhotos.DeletePhotoRequest;
import com.petra.final_exam_work.dto.requestDto.contributor.editUploadedPhotos.EditCoverPhotoRequest;
import com.petra.final_exam_work.dto.requestDto.contributor.editUploadedPhotos.EditTitleAndDescriptionRequest;
import com.petra.final_exam_work.dto.responseDto.contributor.EditAlbum.EditCoverPhotoResponse;
import com.petra.final_exam_work.dto.responseDto.contributor.EditAlbum.EditTitleAndDescriptionResponse;
import com.petra.final_exam_work.entity.enums.ContributorStatus;
import com.petra.final_exam_work.entity.junktionTables.photoAlbumPhoto.PhotoAlbumPhoto;
import com.petra.final_exam_work.entity.photo.Photo;
import com.petra.final_exam_work.entity.photo.PhotoAlbum;
import com.petra.final_exam_work.entity.user.User;
import com.petra.final_exam_work.exception.ApiException;
import com.petra.final_exam_work.repository.PhotoAlbumPhotoRepository;
import com.petra.final_exam_work.repository.PhotoAlbumRepository;
import com.petra.final_exam_work.repository.PhotoRepository;
import com.petra.final_exam_work.repository.UserRepository;
import com.petra.final_exam_work.security.CustomUserDetails;
import com.petra.final_exam_work.service.AlbumSecurityService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ContributorEditAlbumService {

    private final EditTitleAndContributorMapper editTitleAndContributorMapper;
    private final EditCoverPhotoMapper editCoverPhotoMapper;
    private final PhotoAlbumRepository photoAlbumRepository;
    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;
    private final PhotoAlbumPhotoRepository photoAlbumPhotoRepository;
    private final AlbumSecurityService albumSecurityService;

    public ContributorEditAlbumService(EditTitleAndContributorMapper editTitleAndContributorMapper, EditCoverPhotoMapper editCoverPhotoMapper, PhotoAlbumRepository photoAlbumRepository, UserRepository userRepository, PhotoRepository photoRepository, PhotoAlbumPhotoRepository photoAlbumPhotoRepository, AlbumSecurityService albumSecurityService) {
        this.editTitleAndContributorMapper = editTitleAndContributorMapper;
        this.editCoverPhotoMapper = editCoverPhotoMapper;
        this.photoAlbumRepository = photoAlbumRepository;
        this.userRepository = userRepository;
        this.photoRepository = photoRepository;
        this.photoAlbumPhotoRepository = photoAlbumPhotoRepository;
        this.albumSecurityService = albumSecurityService;
    }

    //######### Edit title and description on uploaded content #######
    @Transactional
    public EditTitleAndDescriptionResponse editTitleAndDescription(
            UUID albumPublicUuid,
            CustomUserDetails userDetails,
            EditTitleAndDescriptionRequest request
    ) {

        UUID publicUuid = userDetails.getPublicUuid();
        User user = userRepository.findByPublicUuid(publicUuid)
                .orElseThrow(() -> new ApiException("User was not found", HttpStatus.NOT_FOUND));


        if (user.getContributorStatus() != ContributorStatus.APPROVED) {
            throw new ApiException(
                    "You are not approved to have access to this data",
                    HttpStatus.FORBIDDEN
            );
        }

        PhotoAlbum album = photoAlbumRepository
                .findByPublicUuid(albumPublicUuid)
                .orElseThrow(() -> new ApiException(
                        "Album not found",
                        HttpStatus.NOT_FOUND
                ));

        if (!album.getOwnedByUser().getId().equals(user.getId())) {
            throw new ApiException(
                    "You do not have access to this album",
                    HttpStatus.FORBIDDEN
            );
        }

        boolean exists = photoAlbumRepository
                .existsByOwnedByUserAndPhotoAlbumNameAndPublicUuidNot(
                        user,
                        request.getPhotoAlbumName(),
                        albumPublicUuid
                );

        if (exists) {
            throw new ApiException(
                    "You already have an album with this name",
                    HttpStatus.BAD_REQUEST
            );
        }

        editTitleAndContributorMapper.updatePhotoAlbum(album, request);

        photoAlbumRepository.save(album);

        return editTitleAndContributorMapper.toDto(album);
    }

    //######### Edit cover photo on uploaded content #######
    @Transactional
    public EditCoverPhotoResponse editCoverPhoto(
            UUID albumPublicUuid,
            CustomUserDetails userDetails,
            EditCoverPhotoRequest request
    ) {
        UUID publicUuid = userDetails.getPublicUuid();
        User user = userRepository.findByPublicUuid(publicUuid)
                .orElseThrow(() -> new ApiException(
                        "User was not found",
                        HttpStatus.NOT_FOUND)
                );


        if (user.getContributorStatus() != ContributorStatus.APPROVED) {
            throw new ApiException(
                    "You are not approved to have access to this data",
                    HttpStatus.FORBIDDEN
            );
        }

        PhotoAlbum album = photoAlbumRepository
                .findByPublicUuid(albumPublicUuid)
                .orElseThrow(() -> new ApiException(
                        "Album not found",
                        HttpStatus.NOT_FOUND
                ));

        if (!album.getOwnedByUser().getId().equals(user.getId())) {
            throw new ApiException(
                    "You do not have access to this album",
                    HttpStatus.FORBIDDEN
            );
        }

        Photo photo = photoRepository
                .findByPublicUuid(request.getCoverPhotoPublicUuid())
                .orElseThrow(() -> new ApiException(
                        "Photo not found",
                        HttpStatus.NOT_FOUND
                ));

        albumSecurityService.assertPhotoBelongsToAlbum(
                albumPublicUuid,
                request.getCoverPhotoPublicUuid()
        );

        album.setCoverPhoto(photo);

        return editCoverPhotoMapper.toDto(album);
    }

    //######### Delete photo from uploaded content #######
    public void deletePhoto(
            UUID albumPublicUuid,
            CustomUserDetails userDetails,
            DeletePhotoRequest request
    ) {
        UUID publicUuid = userDetails.getPublicUuid();
        User user = userRepository.findByPublicUuid(publicUuid)
                .orElseThrow(() -> new ApiException(
                        "User was not found",
                        HttpStatus.NOT_FOUND)
                );


        if (user.getContributorStatus() != ContributorStatus.APPROVED) {
            throw new ApiException(
                    "You are not approved to have access to this data",
                    HttpStatus.FORBIDDEN
            );
        }

        PhotoAlbum album = photoAlbumRepository
                .findByPublicUuid(albumPublicUuid)
                .orElseThrow(() -> new ApiException(
                        "Album not found",
                        HttpStatus.NOT_FOUND
                ));

        if (!album.getOwnedByUser().getId().equals(user.getId())) {
            throw new ApiException(
                    "You do not have access to this album",
                    HttpStatus.FORBIDDEN
            );
        }

        Photo photo = photoRepository
                .findByPublicUuid(request.getPhotoPublicUuid())
                .orElseThrow(() -> new ApiException(
                        "Photo not found",
                        HttpStatus.NOT_FOUND
                ));

        boolean exists = photoAlbumPhotoRepository.existsByPhotoAndPhotoAlbum(photo, album);

        if (!exists) {
            throw new ApiException(
                    "Photo does not belong to this album",
                    HttpStatus.FORBIDDEN
            );
        }

        PhotoAlbumPhoto photoAlbumPhoto = photoAlbumPhotoRepository
                .findByPhotoAndPhotoAlbum(photo, album)
                .orElseThrow(() -> new ApiException(
                "Photo has no position in the album",
                HttpStatus.NOT_FOUND
        ));

        //make sure there are still a min of 7 photos in album.
        long totalPhotos = photoAlbumPhotoRepository.countByPhotoAlbum(album);

        if (totalPhotos <= 7) {
            throw new ApiException(
                    "Album must have at least 7 photos",
                    HttpStatus.BAD_REQUEST
            );
        }

       // check if photo getting removed is a cover photo.
       boolean wasCoverPhoto =
               album.getCoverPhoto() != null &&
               album.getCoverPhoto().getPublicUuid().equals(photo.getPublicUuid());

       // remove from db first the link between photo and album , then the actual photo
       photoAlbumPhotoRepository.delete(photoAlbumPhoto);
       photoRepository.delete(photo);

       // After delete reset position starting with 0 and up.
        List<PhotoAlbumPhoto> remaining =
                photoAlbumPhotoRepository
                        .findByPhotoAlbumOrderByPositionAsc(album);

        // reindex
        for (int i = 0; i < remaining.size(); i++) {
            remaining.get(i).setPosition(i);
        }

        // If photo removed was a cover-photo,
        // first remaining photo in album become new cover-photo
        if (wasCoverPhoto) {

            if (remaining.isEmpty()) {
                album.setCoverPhoto(null);
            } else {
                album.setCoverPhoto(
                        remaining.get(0).getPhoto()
                );
            }
        }

        photoAlbumRepository.save(album);
    }
}
