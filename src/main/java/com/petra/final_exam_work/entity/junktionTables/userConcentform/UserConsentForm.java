package com.petra.final_exam_work.entity.junktionTables.userConcentform;

import com.petra.final_exam_work.entity.consentForm.ConsentForm;
import com.petra.final_exam_work.entity.consentForm.ConsentStatus;
import com.petra.final_exam_work.entity.user.User;
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
    @Column(name = "consent_status", nullable = false)
    private ConsentStatus consentStatus;

    public UserConsentForm() {
    }

    public UserConsentFormId getId() {
        return id;
    }

    public void setId(UserConsentFormId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ConsentForm getConsentForm() {
        return consentForm;
    }

    public void setConsentForm(ConsentForm consentForm) {
        this.consentForm = consentForm;
    }

    public ConsentStatus getConsentStatus() {
        return consentStatus;
    }

    public void setConsentStatus(ConsentStatus consentStatus) {
        this.consentStatus = consentStatus;
    }
}
