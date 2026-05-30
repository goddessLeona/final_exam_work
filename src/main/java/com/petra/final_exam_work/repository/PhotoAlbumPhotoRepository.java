package com.petra.final_exam_work.repository;

import com.petra.final_exam_work.entity.junktionTables.photoAlbumPhoto.PhotoAlbumPhoto;
import com.petra.final_exam_work.entity.photo.Photo;
import com.petra.final_exam_work.entity.photo.PhotoAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhotoAlbumPhotoRepository extends JpaRepository<PhotoAlbumPhoto, Long> {

    List<PhotoAlbumPhoto> findByPhotoAlbumOrderByPositionAsc(PhotoAlbum album);

    boolean existsByPhotoAndPhotoAlbum(Photo photo, PhotoAlbum album);

    Optional<PhotoAlbumPhoto> findByPhoto(Photo photo);

    long countByPhotoAlbum(PhotoAlbum album);

    Optional<PhotoAlbumPhoto> findByPhotoAndPhotoAlbum(Photo photo, PhotoAlbum album);

    @Query("""
        SELECT COALESCE(MAX(p.position), -1)
        FROM PhotoAlbumPhoto p
        WHERE p.photoAlbum = :album
    """)
    Integer findMaxPositionByPhotoAlbum(@Param("album")PhotoAlbum album);

}
