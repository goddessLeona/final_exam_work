package com.petra.final_exam_work.dto.responseDto.AdminDashboardConsentFormresponse;

import com.petra.final_exam_work.entity.consentForm.ConsentFormStatus;
import com.petra.final_exam_work.entity.consentForm.ReviewStatus;

public class AdminConsentFormItem {

    private String documentId;
    private String documentIdFace;
    private String documentfff;

    private ReviewStatus idCardReviewed;
    private ReviewStatus idFaceReviewed;
    private ReviewStatus facefffReviewed;
    private Boolean approvedRules;

    private ConsentFormStatus consentFormStatus;

    public AdminConsentFormItem() {
    }

    public AdminConsentFormItem(String documentId, String documentIdFace, String documentfff,
                                ReviewStatus idCardReviewed, ReviewStatus idFaceReviewed,
                                ReviewStatus facefffReviewed, Boolean approvedRules, ConsentFormStatus consentFormStatus) {
        this.documentId = documentId;
        this.documentIdFace = documentIdFace;
        this.documentfff = documentfff;
        this.idCardReviewed = idCardReviewed;
        this.idFaceReviewed = idFaceReviewed;
        this.facefffReviewed = facefffReviewed;
        this.approvedRules = approvedRules;
        this.consentFormStatus = consentFormStatus;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getDocumentIdFace() {
        return documentIdFace;
    }

    public void setDocumentIdFace(String documentIdFace) {
        this.documentIdFace = documentIdFace;
    }

    public String getDocumentfff() {
        return documentfff;
    }

    public void setDocumentfff(String documentfff) {
        this.documentfff = documentfff;
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
}
