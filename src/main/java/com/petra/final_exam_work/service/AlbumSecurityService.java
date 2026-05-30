package com.petra.final_exam_work.service;

import com.petra.final_exam_work.entity.photo.Photo;
import com.petra.final_exam_work.entity.photo.PhotoAlbum;
import com.petra.final_exam_work.exception.ApiException;
import com.petra.final_exam_work.repository.PhotoAlbumPhotoRepository;
import com.petra.final_exam_work.repository.PhotoAlbumRepository;
import com.petra.final_exam_work.repository.PhotoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AlbumSecurityService {

    private final PhotoAlbumPhotoRepository photoAlbumPhotoRepository;
    private final PhotoRepository photoRepository;
    private final PhotoAlbumRepository photoAlbumRepository;

    public AlbumSecurityService(PhotoAlbumPhotoRepository photoAlbumPhotoRepository, PhotoRepository photoRepository, PhotoAlbumRepository photoAlbumRepository) {
        this.photoAlbumPhotoRepository = photoAlbumPhotoRepository;
        this.photoRepository = photoRepository;
        this.photoAlbumRepository = photoAlbumRepository;
    }

    public void assertPhotoBelongsToAlbum(
            UUID albumPublicUuid,
            UUID photoPublicUuid
    ) {

        PhotoAlbum album = photoAlbumRepository
                .findByPublicUuid(albumPublicUuid)
                .orElseThrow(() -> new ApiException(
                        "Album not found",
                        HttpStatus.NOT_FOUND
                ));

        Photo photo = photoRepository.findByPublicUuid(photoPublicUuid)
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
    }
}
