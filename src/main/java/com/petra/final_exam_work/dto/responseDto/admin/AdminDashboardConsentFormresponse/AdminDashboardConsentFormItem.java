package com.petra.final_exam_work.dto.responseDto.admin.AdminDashboardConsentFormresponse;

import com.petra.final_exam_work.entity.consentForm.ConsentFormStatus;

import java.util.UUID;

public class AdminDashboardConsentFormItem {

    private String username;

    private long documentsPending;
    private long documentsApproved;
    private long documentsRejected;

    private ConsentFormStatus consentFormStatus;

    private UUID consentFormId;

    public AdminDashboardConsentFormItem() {
    }

    public AdminDashboardConsentFormItem(String username, long documentsPending, long documentsApproved, long documentsRejected,
                                         ConsentFormStatus consentFormStatus, UUID consentFormId) {
        this.username = username;
        this.documentsPending = documentsPending;
        this.documentsApproved = documentsApproved;
        this.documentsRejected = documentsRejected;
        this.consentFormStatus = consentFormStatus;
        this.consentFormId = consentFormId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getDocumentsPending() {
        return documentsPending;
    }

    public void setDocumentsPending(long documentsPending) {
        this.documentsPending = documentsPending;
    }

    public long getDocumentsApproved() {
        return documentsApproved;
    }

    public void setDocumentsApproved(long documentsApproved) {
        this.documentsApproved = documentsApproved;
    }

    public long getDocumentsRejected() {
        return documentsRejected;
    }

    public void setDocumentsRejected(long documentsRejected) {
        this.documentsRejected = documentsRejected;
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
