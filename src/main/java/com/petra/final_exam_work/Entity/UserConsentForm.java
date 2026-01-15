package com.petra.final_exam_work.Entity;

import com.petra.final_exam_work.Entity.Enum.ConsentStatus;
import com.petra.final_exam_work.config.ConsentStatusConverter;
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

    @Convert(converter = ConsentStatusConverter.class)
    @Column(name = "status", nullable = false)
    private ConsentStatus consentStatus;

    public UserConsentForm() {
    }
}
