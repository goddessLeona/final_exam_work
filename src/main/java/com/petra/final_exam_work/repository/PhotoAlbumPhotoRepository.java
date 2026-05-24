package com.petra.final_exam_work.repository;

import com.petra.final_exam_work.entity.junktionTables.photoAlbumPhoto.PhotoAlbumPhoto;
import com.petra.final_exam_work.entity.photo.Photo;
import com.petra.final_exam_work.entity.photo.PhotoAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface PhotoAlbumPhotoRepository extends JpaRepository<PhotoAlbumPhoto, Long> {

    List<PhotoAlbumPhoto> findByPhotoAlbumOrderByPositionAsc(PhotoAlbum album);

    boolean existsByPhotoAndPhotoAlbum(Photo photo, PhotoAlbum album);

}
