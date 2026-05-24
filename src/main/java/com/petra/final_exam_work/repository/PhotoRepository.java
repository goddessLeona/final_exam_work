package com.petra.final_exam_work.repository;

import com.petra.final_exam_work.entity.photo.Photo;
import com.petra.final_exam_work.entity.photo.PhotoAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    Optional<Photo> findByPublicUuid(UUID coverPhotoPublicUuid);

}
