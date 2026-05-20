package com.petra.final_exam_work.repository;

import com.petra.final_exam_work.entity.consentForm.ConsentFormStatus;
import com.petra.final_exam_work.entity.enums.ContentStatus;
import com.petra.final_exam_work.entity.enums.ContentType;
import com.petra.final_exam_work.entity.junktionTables.userConcentform.UserConsentFormId;
import com.petra.final_exam_work.entity.photo.PhotoAlbum;
import com.petra.final_exam_work.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PhotoAlbumRepository extends JpaRepository<PhotoAlbum, UserConsentFormId> {

    long countByOwnedByUser_id(Long userId);

    @Query("SELECT ucf.consentFormStatus FROM UserConsentForm ucf " +
            "WHERE ucf.user.id = :userId " +
            "AND ucf.consentFormStatus IN ('PENDING', 'APPROVED')")
    Optional<ConsentFormStatus> findStatusByUserId(@Param("userId") Long userId);

    Page<PhotoAlbum> findByOwnedByUserAndContentStatus(User user, ContentStatus status, Pageable pageable);

    Object countByOwnedByUserAndContentStatus(User user, ContentStatus status);

    Page<PhotoAlbum> findAllByContentStatusAndContentTypeOrderByPublishedAtDesc(ContentStatus contentStatus, ContentType contentType, Pageable pageable);

    boolean existsByOwnedByUserAndContentStatus(User user, ContentStatus contentStatus);

    Optional<PhotoAlbum> findByPublicUuidAndContentStatus(UUID publicUuid, ContentStatus contentStatus);

}
