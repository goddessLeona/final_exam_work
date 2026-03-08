package com.petra.final_exam_work.dto.responseDto;

import com.petra.final_exam_work.entity.consentForm.ConsentStatus;

public class ContributorConsentFormResponse {

    private String idCardFilePath;
    private Boolean idCardReviewed;
    private String idFaceFilePath;
    private Boolean idFaceReviewed;
    private String facefffFilePath;
    private Boolean facefffReviewed;
    private Boolean approvedRules;
    private ConsentStatus consentStatus;
    private boolean contributor;

    public ContributorConsentFormResponse() {
    }

    public ContributorConsentFormResponse(String idCardFilePath, Boolean idCardReviewed, String idFaceFilePath,
                                          Boolean idFaceReviewed, String facefffFilePath, Boolean facefffReviewed,
                                          Boolean approvedRules, ConsentStatus consentStatus, boolean contributor) {
        this.idCardFilePath = idCardFilePath;
        this.idCardReviewed = idCardReviewed;
        this.idFaceFilePath = idFaceFilePath;
        this.idFaceReviewed = idFaceReviewed;
        this.facefffFilePath = facefffFilePath;
        this.facefffReviewed = facefffReviewed;
        this.approvedRules = approvedRules;
        this.consentStatus = consentStatus;
        this.contributor = contributor;
    }

    public String getIdCardFilePath() {
        return idCardFilePath;
    }

    public void setIdCardFilePath(String idCardFilePath) {
        this.idCardFilePath = idCardFilePath;
    }

    public Boolean getIdCardReviewed() {
        return idCardReviewed;
    }

    public void setIdCardReviewed(Boolean idCardReviewed) {
        this.idCardReviewed = idCardReviewed;
    }

    public String getIdFaceFilePath() {
        return idFaceFilePath;
    }

    public void setIdFaceFilePath(String idFaceFilePath) {
        this.idFaceFilePath = idFaceFilePath;
    }

    public Boolean getIdFaceReviewed() {
        return idFaceReviewed;
    }

    public void setIdFaceReviewed(Boolean idFaceReviewed) {
        this.idFaceReviewed = idFaceReviewed;
    }

    public String getFacefffFilePath() {
        return facefffFilePath;
    }

    public void setFacefffFilePath(String facefffFilePath) {
        this.facefffFilePath = facefffFilePath;
    }

    public Boolean getFacefffReviewed() {
        return facefffReviewed;
    }

    public void setFacefffReviewed(Boolean facefffReviewed) {
        this.facefffReviewed = facefffReviewed;
    }

    public Boolean getApprovedRules() {
        return approvedRules;
    }

    public void setApprovedRules(Boolean approvedRules) {
        this.approvedRules = approvedRules;
    }

    public ConsentStatus getConsentStatus() {
        return consentStatus;
    }

    public void setConsentStatus(ConsentStatus consentStatus) {
        this.consentStatus = consentStatus;
    }

    public boolean isContributor() {
        return contributor;
    }

    public void setContributor(boolean contributor) {
        this.contributor = contributor;
    }
}
