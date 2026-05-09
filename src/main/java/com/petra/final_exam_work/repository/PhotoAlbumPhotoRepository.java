package com.petra.final_exam_work.repository;

import com.petra.final_exam_work.entity.junktionTables.photoAlbumPhoto.PhotoAlbumPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoAlbumPhotoRepository extends JpaRepository<PhotoAlbumPhoto, Long> {
}
