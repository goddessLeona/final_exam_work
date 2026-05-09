package com.petra.final_exam_work.dto.responseDto;

import com.petra.final_exam_work.entity.consentForm.ConsentFormStatus;
import com.petra.final_exam_work.entity.enums.ReviewStatus;
import com.petra.final_exam_work.entity.enums.ContributorStatus;

public class ContributorConsentFormResponse {

    private String idCardFilePath;
    private ReviewStatus idCardReviewed;
    private String idCardMessage;
    private String idFaceFilePath;
    private ReviewStatus idFaceReviewed;
    private String idFaceMessage;
    private String facefffFilePath;
    private ReviewStatus facefffReviewed;
    private String facefffMessage;
    private Boolean approvedRules;
    private ConsentFormStatus consentFormStatus;
    private ContributorStatus status;

    public ContributorConsentFormResponse() {
    }

    public ContributorConsentFormResponse(String idCardFilePath, ReviewStatus idCardReviewed, String idCardMessage,
                                          String idFaceFilePath, ReviewStatus idFaceReviewed, String idFaceMessage,
                                          String facefffFilePath, ReviewStatus facefffReviewed, String facefffMessage,
                                          Boolean approvedRules, ConsentFormStatus consentFormStatus, ContributorStatus status) {
        this.idCardFilePath = idCardFilePath;
        this.idCardReviewed = idCardReviewed;
        this.idCardMessage = idCardMessage;
        this.idFaceFilePath = idFaceFilePath;
        this.idFaceReviewed = idFaceReviewed;
        this.idFaceMessage = idFaceMessage;
        this.facefffFilePath = facefffFilePath;
        this.facefffReviewed = facefffReviewed;
        this.facefffMessage = facefffMessage;
        this.approvedRules = approvedRules;
        this.consentFormStatus = consentFormStatus;
        this.status = status;
    }

    public String getIdCardFilePath() {
        return idCardFilePath;
    }

    public void setIdCardFilePath(String idCardFilePath) {
        this.idCardFilePath = idCardFilePath;
    }

    public ReviewStatus getIdCardReviewed() {
        return idCardReviewed;
    }

    public void setIdCardReviewed(ReviewStatus idCardReviewed) {
        this.idCardReviewed = idCardReviewed;
    }

    public String getIdCardMessage() {
        return idCardMessage;
    }

    public void setIdCardMessage(String idCardMessage) {
        this.idCardMessage = idCardMessage;
    }

    public String getIdFaceFilePath() {
        return idFaceFilePath;
    }

    public void setIdFaceFilePath(String idFaceFilePath) {
        this.idFaceFilePath = idFaceFilePath;
    }

    public ReviewStatus getIdFaceReviewed() {
        return idFaceReviewed;
    }

    public void setIdFaceReviewed(ReviewStatus idFaceReviewed) {
        this.idFaceReviewed = idFaceReviewed;
    }

    public String getIdFaceMessage() {
        return idFaceMessage;
    }

    public void setIdFaceMessage(String idFaceMessage) {
        this.idFaceMessage = idFaceMessage;
    }

    public String getFacefffFilePath() {
        return facefffFilePath;
    }

    public void setFacefffFilePath(String facefffFilePath) {
        this.facefffFilePath = facefffFilePath;
    }

    public ReviewStatus getFacefffReviewed() {
        return facefffReviewed;
    }

    public void setFacefffReviewed(ReviewStatus facefffReviewed) {
        this.facefffReviewed = facefffReviewed;
    }

    public String getFacefffMessage() {
        return facefffMessage;
    }

    public void setFacefffMessage(String facefffMessage) {
        this.facefffMessage = facefffMessage;
    }

    public Boolean getApprovedRules() {
        return approvedRules;
    }

    public void setApprovedRules(Boolean approvedRules) {
        this.approvedRules = approvedRules;
    }

    public ConsentFormStatus getConsentFormStatus() {
        return consentFormStatus;
    }

    public void setConsentFormStatus(ConsentFormStatus consentFormStatus) {
        this.consentFormStatus = consentFormStatus;
    }

    public ContributorStatus getStatus() {
        return status;
    }

    public void setStatus(ContributorStatus status) {
        this.status = status;
    }
}
