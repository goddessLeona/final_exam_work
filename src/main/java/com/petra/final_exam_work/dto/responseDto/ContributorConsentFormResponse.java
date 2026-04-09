package com.petra.final_exam_work.dto.responseDto;

import com.petra.final_exam_work.entity.consentForm.ConsentFormStatus;
import com.petra.final_exam_work.entity.consentForm.ReviewStatus;

public class ContributorConsentFormResponse {

    private String idCardFilePath;
    private ReviewStatus idCardReviewed;
    private String idFaceFilePath;
    private ReviewStatus idFaceReviewed;
    private String facefffFilePath;
    private ReviewStatus facefffReviewed;
    private Boolean approvedRules;
    private ConsentFormStatus consentFormStatus;
    private boolean contributor;

    public ContributorConsentFormResponse() {
    }

    public ContributorConsentFormResponse(String idCardFilePath, ReviewStatus idCardReviewed, String idFaceFilePath,
                                          ReviewStatus idFaceReviewed, String facefffFilePath,
                                          ReviewStatus facefffReviewed, Boolean approvedRules,
                                          ConsentFormStatus consentFormStatus, boolean contributor) {
        this.idCardFilePath = idCardFilePath;
        this.idCardReviewed = idCardReviewed;
        this.idFaceFilePath = idFaceFilePath;
        this.idFaceReviewed = idFaceReviewed;
        this.facefffFilePath = facefffFilePath;
        this.facefffReviewed = facefffReviewed;
        this.approvedRules = approvedRules;
        this.consentFormStatus = consentFormStatus;
        this.contributor = contributor;
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

    public boolean isContributor() {
        return contributor;
    }

    public void setContributor(boolean contributor) {
        this.contributor = contributor;
    }
}
