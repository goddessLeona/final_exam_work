package com.petra.final_exam_work.dto.responseDto.admin.AdminConsentFormResponse;

import com.petra.final_exam_work.entity.consentForm.ConsentFormStatus;

import java.util.UUID;

public class ConsentFormDataResponse {

    private String username;

    private DocumentDto documentIdCard;
    private DocumentDto documentIdCardFace;
    private DocumentDto documentFaceFFF;
    private Boolean approvedRules;

    private ConsentFormStatus consentFormStatus;
    private UUID consentFormId;

    public ConsentFormDataResponse() {
    }

    public ConsentFormDataResponse(String username, DocumentDto documentIdCard, DocumentDto documentIdCardFace,
                                   DocumentDto documentFaceFFF, Boolean approvedRules,
                                   ConsentFormStatus consentFormStatus, UUID consentFormId) {
        this.username = username;
        this.documentIdCard = documentIdCard;
        this.documentIdCardFace = documentIdCardFace;
        this.documentFaceFFF = documentFaceFFF;
        this.approvedRules = approvedRules;
        this.consentFormStatus = consentFormStatus;
        this.consentFormId = consentFormId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public DocumentDto getDocumentIdCard() {
        return documentIdCard;
    }

    public void setDocumentIdCard(DocumentDto documentIdCard) {
        this.documentIdCard = documentIdCard;
    }

    public DocumentDto getDocumentIdCardFace() {
        return documentIdCardFace;
    }

    public void setDocumentIdCardFace(DocumentDto documentIdCardFace) {
        this.documentIdCardFace = documentIdCardFace;
    }

    public DocumentDto getDocumentFaceFFF() {
        return documentFaceFFF;
    }

    public void setDocumentFaceFFF(DocumentDto documentFaceFFF) {
        this.documentFaceFFF = documentFaceFFF;
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

    public UUID getConsentFormId() {
        return consentFormId;
    }

    public void setConsentFormId(UUID consentFormId) {
        this.consentFormId = consentFormId;
    }
}
