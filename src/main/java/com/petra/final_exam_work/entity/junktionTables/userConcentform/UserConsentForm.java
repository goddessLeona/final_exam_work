package com.petra.final_exam_work.entity.junktionTables.userConcentform;

import com.petra.final_exam_work.entity.consentForm.ConsentForm;
import com.petra.final_exam_work.entity.consentForm.ConsentFormStatus;
import com.petra.final_exam_work.entity.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "users_consent_forms")
public class UserConsentForm {

    @EmbeddedId
    private UserConsentFormId id = new UserConsentFormId();

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("consentFormId")
    @JoinColumn(name = "consent_form_id")
    private ConsentForm consentForm;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "consent_form_status",columnDefinition = "consent_form_status", nullable = false)
    private ConsentFormStatus consentFormStatus;

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

    public ConsentFormStatus getConsentFormStatus() {
        return consentFormStatus;
    }

    public void setConsentFormStatus(ConsentFormStatus consentFormStatus) {
        this.consentFormStatus = consentFormStatus;
    }
}
