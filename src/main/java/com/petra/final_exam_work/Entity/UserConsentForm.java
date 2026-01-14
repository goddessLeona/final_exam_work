package com.petra.final_exam_work.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users_consent_forms")
public class UserConsentForm {

    @EmbeddedId
    private UserConsentFormId id;

    @ManyToMany
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @MapsId("consentFormId")
    @JoinColumn(name = "consent_form_id")
    private ConsentForm consentForm;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CosentStatus status;

    public UserConsentForm() {
    }
}
