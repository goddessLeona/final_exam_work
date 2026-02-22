package com.petra.final_exam_work.repository;

import com.petra.final_exam_work.entity.consentForm.ConsentStatus;
import com.petra.final_exam_work.entity.junktionTables.userConcentform.UserConsentFormId;
import com.petra.final_exam_work.entity.photo.PhotoAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhotoAlbumRepository extends JpaRepository<PhotoAlbum, UserConsentFormId> {

    long countByOwnedByUser_id(Long userId);

    @Query("SELECT ucf.consentStatus FROM UserConsentForm ucf " +
            "WHERE ucf.user.id = :userId " +
            "AND ucf.consentStatus IN ('PENDING', 'APPROVED')")
    Optional<ConsentStatus> findStatusByUserId(@Param("userId") Long userId);
}
