package com.petra.final_exam_work.repository;

import com.petra.final_exam_work.entity.consentForm.ConsentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserConsentFormRepository extends JpaRepository<UserConsentFormRepository, Long> {


    Optional<ConsentStatus> findStatusByUserId(Long userId);

}
