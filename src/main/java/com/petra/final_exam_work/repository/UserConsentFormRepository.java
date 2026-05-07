package com.petra.final_exam_work.repository;

import com.petra.final_exam_work.entity.consentForm.ConsentFormStatus;
import com.petra.final_exam_work.entity.junktionTables.userConcentform.UserConsentForm;
import com.petra.final_exam_work.entity.junktionTables.userConcentform.UserConsentFormId;
import com.petra.final_exam_work.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserConsentFormRepository extends JpaRepository<UserConsentForm, UserConsentFormId> {

    @Query("SELECT ucf.consentFormStatus FROM UserConsentForm ucf " +
            "WHERE ucf.user.id = :userId ")
    Optional<ConsentFormStatus> findStatusByUser(@Param("userId") Long userId);

    Optional<UserConsentForm> findByUser(User user);

    List<UserConsentForm> findAllByUser(User user);

    Optional <UserConsentForm> findByConsentForm_PublicUuid(UUID publicUuid);
}
