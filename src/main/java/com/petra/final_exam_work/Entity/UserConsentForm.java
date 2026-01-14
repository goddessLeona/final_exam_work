package com.petra.final_exam_work.Entity;

import com.petra.final_exam_work.Entity.Enum.ConsentStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "users_consent_forms")
public class UserConsentForm {

    @EmbeddedId
    private UserConsentFormId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("consentFormId")
    @JoinColumn(name = "consent_form_id")
    private ConsentForm consentForm;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConsentStatus status;

    public UserConsentForm() {
    }
}
