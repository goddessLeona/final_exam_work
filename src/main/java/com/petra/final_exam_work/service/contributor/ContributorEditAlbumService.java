package com.petra.final_exam_work.service.contributor;

import com.petra.final_exam_work.dto.mapperDto.contributor.editAlbum.EditCoverPhotoMapper;
import com.petra.final_exam_work.dto.mapperDto.contributor.editAlbum.EditTitleAndContributorMapper;
import com.petra.final_exam_work.dto.mapperDto.member.GetPhotoAlbumsMapper;
import com.petra.final_exam_work.dto.requestDto.contributor.editUploadedPhotos.*;
import com.petra.final_exam_work.dto.responseDto.contributor.EditAlbum.EditCoverPhotoResponse;
import com.petra.final_exam_work.dto.responseDto.contributor.EditAlbum.EditTitleAndDescriptionResponse;
import com.petra.final_exam_work.dto.responseDto.members.GetPhotoAlbumsResponse;
import com.petra.final_exam_work.entity.enums.ContentStatus;
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
import com.petra.final_exam_work.service.FileStorageService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    private final FileStorageService fileStorageService;
    private final GetPhotoAlbumsMapper getPhotoAlbumsMapper;

    public ContributorEditAlbumService(EditTitleAndContributorMapper editTitleAndContributorMapper, EditCoverPhotoMapper editCoverPhotoMapper, PhotoAlbumRepository photoAlbumRepository, UserRepository userRepository, PhotoRepository photoRepository, PhotoAlbumPhotoRepository photoAlbumPhotoRepository, AlbumSecurityService albumSecurityService, FileStorageService fileStorageService, GetPhotoAlbumsMapper getPhotoAlbumsMapper) {
        this.editTitleAndContributorMapper = editTitleAndContributorMapper;
        this.editCoverPhotoMapper = editCoverPhotoMapper;
        this.photoAlbumRepository = photoAlbumRepository;
        this.userRepository = userRepository;
        this.photoRepository = photoRepository;
        this.photoAlbumPhotoRepository = photoAlbumPhotoRepository;
        this.albumSecurityService = albumSecurityService;
        this.fileStorageService = fileStorageService;
        this.getPhotoAlbumsMapper = getPhotoAlbumsMapper;
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


        PhotoAlbumPhoto photoAlbumPhoto = photoAlbumPhotoRepository
                .findByPhotoAndPhotoAlbum(photo, album)
                .orElseThrow(() -> new ApiException(
                "Photo does not belong to this album",
                HttpStatus.FORBIDDEN
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

    //####### ADD/POST new photo to a album ########
    @Transactional
    public GetPhotoAlbumsResponse addPhoto(
            UUID albumPublicUuid,
            CustomUserDetails userDetails,
            AddPhotoRequest request
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

        //make sure there are less than 30 photos in album.
        long totalPhotos = photoAlbumPhotoRepository.countByPhotoAlbum(album);

        if (totalPhotos >= 30) {
            throw new ApiException(
                    "Album can only contain 30 photos",
                    HttpStatus.BAD_REQUEST
            );
        }

        //upload photo and save in album
        List<Photo> savedPhotos = new ArrayList<>();

        for (MultipartFile file : request.getPhotos()) {

            String path = handleImageUpload(
                    file,
                    user.getPublicUuid(),
                    "albums/photo/" + album.getPublicUuid(),
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

        // add uploaded new photo after the last photo in album
        Integer nextPosition =
                Optional.ofNullable(
                photoAlbumPhotoRepository
                        .findMaxPositionByPhotoAlbum(album)
                ) .orElse(-1) +1;

        //save the link between photo and the photo album

        List<PhotoAlbumPhoto> links = new ArrayList<>();

        for (Photo photo : savedPhotos) {

            PhotoAlbumPhoto link = new PhotoAlbumPhoto();

            link.setPhotoAlbum(album);
            link.setPhoto(photo);
            link.setPosition(nextPosition++);

            links.add(link);
        }
        photoAlbumPhotoRepository.saveAll(links);

        List<PhotoAlbumPhoto> updatedLinks =
                photoAlbumPhotoRepository
                        .findByPhotoAlbumOrderByPositionAsc(album);

        List<Photo> updatedPhotos = updatedLinks.stream()
                .map(PhotoAlbumPhoto::getPhoto)
                .toList();

        return getPhotoAlbumsMapper.toDto(
                album,
                updatedPhotos
        );

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

    //######### Reorder photo in album #######
    @Transactional
    public GetPhotoAlbumsResponse reorderPhotos (
            UUID albumPublicUuid,
            CustomUserDetails userDetails,
            ReorderPhotosRequest request
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


        PhotoAlbumPhoto photoAlbumPhoto = photoAlbumPhotoRepository
                .findByPhotoAndPhotoAlbum(photo, album)
                .orElseThrow(() -> new ApiException(
                        "Photo does not belong to this album",
                        HttpStatus.FORBIDDEN
                ));

        // Reorder photos in album
        List<PhotoAlbumPhoto> photos =
                photoAlbumPhotoRepository.findByPhotoAlbumOrderByPositionAsc(album);

        photos.remove(photoAlbumPhoto);

        int targetPosition= request.getTargetPosition();

        if (targetPosition <0 || targetPosition > photos.size()) {
            throw new ApiException(
                    "Invalid target position",
                    HttpStatus.BAD_REQUEST
            );
        }

        photos.add(targetPosition, photoAlbumPhoto);

        //reindex photos in album
        for (int i = 0; i < photos.size(); i++) {
            photos.get(i).setPosition(i);
        }

        photoAlbumPhotoRepository.saveAll(photos);

        List<Photo> updatedPhotos = photos.stream()
                .map(PhotoAlbumPhoto::getPhoto)
                .toList();

        return getPhotoAlbumsMapper.toDto(
                album,
                updatedPhotos
        );
    }

    // Change the status on the album
    @Transactional
    public void editStatus(
            UUID albumPublicUuid,
            CustomUserDetails userDetails,
            EditStatusAlbumRequest request
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

        // change status
        ContentStatus newStatus = request.getStatus();

        if (album.getContentStatus() == newStatus) {
            throw new ApiException(
                    "Album already has this status",
                    HttpStatus.BAD_REQUEST
            );
        }

        if (newStatus == ContentStatus.ARCHIVED) {
            album.setContentStatus(ContentStatus.ARCHIVED);
            album.setArchived_at(Instant.now());
            return;
        }

        album.setContentStatus(newStatus);
    }

    //change or add publishing date
    @Transactional
    public GetPhotoAlbumsResponse editPublishingDate(
            UUID albumPublicUuid,
            CustomUserDetails userDetails,
            EditPublishedDateRequest request
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

        //update or scheduled content
        Instant now = Instant.now();
        Instant publishedAt = request.getPublishedAt();

        if (
                publishedAt != null &&
                        publishedAt.isBefore(now.minusSeconds(60))
        ){
            throw new ApiException(
                    "Published date can not be in the past",
                    HttpStatus.BAD_REQUEST
            );
        }

        album.setPublishedAt(publishedAt);

        if (publishedAt == null) {
            album.setContentStatus(ContentStatus.DRAFT);
        } else if (!publishedAt.isAfter(now)) {
            album.setContentStatus(ContentStatus.PUBLISHED);
        } else {
            album.setContentStatus(ContentStatus.SCHEDULED);
        }

        List<PhotoAlbumPhoto> photos =
                photoAlbumPhotoRepository.findByPhotoAlbumOrderByPositionAsc(album);

        return getPhotoAlbumsMapper.toDto(
                album,
                photos.stream()
                        .map(PhotoAlbumPhoto::getPhoto)
                        .toList()
        );
    }
}


