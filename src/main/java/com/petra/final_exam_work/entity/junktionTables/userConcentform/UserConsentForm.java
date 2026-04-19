package com.petra.final_exam_work.entity.junktionTables.userConcentform;

import com.petra.final_exam_work.entity.consentForm.ConsentForm;
import com.petra.final_exam_work.entity.consentForm.ConsentFormStatus;
import com.petra.final_exam_work.entity.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;

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

    @Column(name = "admin_comment")
    private String adminComment;

    @Column(name = "created_at", updatable = false, insertable = false)
    private Instant createdAt;

    @Column(name = "updated_at", updatable = false, insertable = false)
    private Instant updatedAt;

    @Column(name = "submitted_at", updatable = true)
    private Instant submittedAt;

    @Column(name = "reviewed_at", updatable = true)
    private Instant reviewedAt;

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

    public String getAdminComment() {
        return adminComment;
    }

    public void setAdminComment(String adminComment) {
        this.adminComment = adminComment;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(Instant submittedAt) {
        this.submittedAt = submittedAt;
    }

    public Instant getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(Instant reviewedAt) {
        this.reviewedAt = reviewedAt;
    }
}
