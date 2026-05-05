package com.petra.final_exam_work.dto.requestDto.admin;

import com.petra.final_exam_work.entity.consentForm.ReviewStatus;
import jakarta.validation.constraints.Size;

public class AdminConsentReviewRequest {

    private ReviewStatus idCardStatus;
    private ReviewStatus idFaceStatus;
    private ReviewStatus facefffStatus;

    @Size(max = 300)
    private String idCardMessage;

    @Size(max = 300)
    private String idFaceMessage;

    @Size(max = 300)
    private String facefffMessage;

    public AdminConsentReviewRequest() {
    }

    public AdminConsentReviewRequest(ReviewStatus idCardStatus, ReviewStatus idFaceStatus, ReviewStatus facefffStatus,
                                     String idCardMessage, String idFaceMessage, String facefffMessage) {
        this.idCardStatus = idCardStatus;
        this.idFaceStatus = idFaceStatus;
        this.facefffStatus = facefffStatus;
        this.idCardMessage = idCardMessage;
        this.idFaceMessage = idFaceMessage;
        this.facefffMessage = facefffMessage;
    }

    public ReviewStatus getIdCardStatus() {
        return idCardStatus;
    }

    public void setIdCardStatus(ReviewStatus idCardStatus) {
        this.idCardStatus = idCardStatus;
    }

    public ReviewStatus getIdFaceStatus() {
        return idFaceStatus;
    }

    public void setIdFaceStatus(ReviewStatus idFaceStatus) {
        this.idFaceStatus = idFaceStatus;
    }

    public ReviewStatus getFacefffStatus() {
        return facefffStatus;
    }

    public void setFacefffStatus(ReviewStatus facefffStatus) {
        this.facefffStatus = facefffStatus;
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
}
