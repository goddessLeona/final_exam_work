package com.petra.final_exam_work.repository;

import com.petra.final_exam_work.entity.photo.PhotoAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoAlbumRepository extends JpaRepository<PhotoAlbum, Long> {

    Integer countByOwnerId(Long userId);

}
