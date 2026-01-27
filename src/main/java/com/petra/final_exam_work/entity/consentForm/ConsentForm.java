package com.petra.final_exam_work.entity.consentForm;

import com.petra.final_exam_work.entity.junktionTables.userConcentform.UserConsentForm;
import jakarta.persistence.*;

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

    @Column(name = "id_card_reviewed", nullable = false)
    private Boolean idCardReviewed = false;

    @Column(name = "id_face_reviewed", nullable = false)
    private Boolean idFaceReviewed = false;

    @Column(name = "face_fff_reviewed", nullable = false)
    private Boolean facefffReviewed = false;

    @OneToMany(mappedBy = "consentForm", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserConsentForm> users = new HashSet<>();

    @PrePersist
    private void prePersist(){
        if(publicUuid == null){
            publicUuid = UUID.randomUUID();
        }
    }

    public ConsentForm() {
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

    public Set<UserConsentForm> getUsers() {
        return users;
    }

    public void setUsers(Set<UserConsentForm> users) {
        this.users = users;
    }
}
