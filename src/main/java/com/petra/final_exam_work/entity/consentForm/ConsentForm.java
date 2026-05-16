package com.petra.final_exam_work.entity.consentForm;

import com.petra.final_exam_work.entity.enums.ReviewStatus;
import com.petra.final_exam_work.entity.junktionTables.userConcentform.UserConsentForm;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;


import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "consent_forms")
public class ConsentForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "public_uuid",
            updatable = false,
            unique = true,
            nullable = false
    )
    private UUID publicUuid;

    @Column(name = "id_card_file_path", nullable = false)
    private String idCardFilePath;

    @Column(name = "id_face_file_path", nullable = false)
    private String idFaceFilePath;

    @Column(name = "face_fff_file_path", nullable = false)
    private String facefffFilePath;

    @Column(name = "approve_rules", nullable = false)
    private Boolean approvedRules;


    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "id_card_reviewed", columnDefinition = "review_status", nullable = false)
    private ReviewStatus idCardReviewed;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "id_face_reviewed", columnDefinition = "review_status", nullable = false)
    private ReviewStatus idFaceReviewed;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "face_fff_reviewed", columnDefinition = "review_status", nullable = false)
    private ReviewStatus facefffReviewed;

    @Column(name = "id_card_message")
    private String idCardMessage;

    @Column(name = "id_face_message")
    private String idFaceMessage;

    @Column(name = "face_fff_message")
    private String facefffMessage;

    @OneToMany(mappedBy = "consentForm", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserConsentForm> users = new HashSet<>();

    @PrePersist
    private void prePersist() {
        if (publicUuid == null) {
            publicUuid = UUID.randomUUID();
        }
    }

    public ConsentForm() {
    }

    public ConsentForm(Long id, UUID publicUuid, String idCardFilePath, String idFaceFilePath, String facefffFilePath,
                       Boolean approvedRules, ReviewStatus idCardReviewed, ReviewStatus idFaceReviewed,
                       ReviewStatus facefffReviewed, String idCardMessage, String idFaceMessage, String facefffMessage,
                       Set<UserConsentForm> users) {
        this.id = id;
        this.publicUuid = publicUuid;
        this.idCardFilePath = idCardFilePath;
        this.idFaceFilePath = idFaceFilePath;
        this.facefffFilePath = facefffFilePath;
        this.approvedRules = approvedRules;
        this.idCardReviewed = idCardReviewed;
        this.idFaceReviewed = idFaceReviewed;
        this.facefffReviewed = facefffReviewed;
        this.idCardMessage = idCardMessage;
        this.idFaceMessage = idFaceMessage;
        this.facefffMessage = facefffMessage;
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getPublicUuid() {
        return publicUuid;
    }

    public void setPublicUuid(UUID publicUuid) {
        this.publicUuid = publicUuid;
    }

    public String getIdCardFilePath() {
        return idCardFilePath;
    }

    public void setIdCardFilePath(String idCardFilePath) {
        this.idCardFilePath = idCardFilePath;
    }

    public String getIdFaceFilePath() {
        return idFaceFilePath;
    }

    public void setIdFaceFilePath(String idFaceFilePath) {
        this.idFaceFilePath = idFaceFilePath;
    }

    public String getFacefffFilePath() {
        return facefffFilePath;
    }

    public void setFacefffFilePath(String facefffFilePath) {
        this.facefffFilePath = facefffFilePath;
    }

    public Boolean getApprovedRules() {
        return approvedRules;
    }

    public void setApprovedRules(Boolean approvedRules) {
        this.approvedRules = approvedRules;
    }

    public ReviewStatus getIdCardReviewed() {
        return idCardReviewed;
    }

    public void setIdCardReviewed(ReviewStatus idCardReviewed) {
        this.idCardReviewed = idCardReviewed;
    }

    public ReviewStatus getIdFaceReviewed() {
        return idFaceReviewed;
    }

    public void setIdFaceReviewed(ReviewStatus idFaceReviewed) {
        this.idFaceReviewed = idFaceReviewed;
    }

    public ReviewStatus getFacefffReviewed() {
        return facefffReviewed;
    }

    public void setFacefffReviewed(ReviewStatus facefffReviewed) {
        this.facefffReviewed = facefffReviewed;
    }

    public String getIdCardMessage() {
        return idCardMessage;
    }

    public void setIdCardMessage(String idCardMessage) {
        this.idCardMessage = idCardMessage;
    }

    public String getIdFaceMessage() {
        return idFaceMessage;
    }

    public void setIdFaceMessage(String idFaceMessage) {
        this.idFaceMessage = idFaceMessage;
    }

    public String getFacefffMessage() {
        return facefffMessage;
    }

    public void setFacefffMessage(String facefffMessage) {
        this.facefffMessage = facefffMessage;
    }

    public Set<UserConsentForm> getUsers() {
        return users;
    }

    public void setUsers(Set<UserConsentForm> users) {
        this.users = users;
    }
}
