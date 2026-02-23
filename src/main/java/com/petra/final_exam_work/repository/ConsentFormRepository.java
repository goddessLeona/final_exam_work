package com.petra.final_exam_work.repository;

import com.petra.final_exam_work.entity.consentForm.ConsentForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsentFormRepository extends JpaRepository<ConsentForm, Long> {
}
