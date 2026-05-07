package com.petra.final_exam_work.dto.responseDto.admin.AdminDashboardConsentFormresponse;

public class AdminDashboardConsentFormResponse {

    private long total;

    private AdminDashboardConsentFormSection pending;
    private AdminDashboardConsentFormSection approved;
    private AdminDashboardConsentFormSection rejected;
    private AdminDashboardConsentFormSection notSubmitted;

    public AdminDashboardConsentFormResponse() {
    }

    public AdminDashboardConsentFormResponse(long total, AdminDashboardConsentFormSection pending, AdminDashboardConsentFormSection approved,
                                             AdminDashboardConsentFormSection rejected, AdminDashboardConsentFormSection notSubmitted) {
        this.total = total;
        this.pending = pending;
        this.approved = approved;
        this.rejected = rejected;
        this.notSubmitted = notSubmitted;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public AdminDashboardConsentFormSection getPending() {
        return pending;
    }

    public void setPending(AdminDashboardConsentFormSection pending) {
        this.pending = pending;
    }

    public AdminDashboardConsentFormSection getApproved() {
        return approved;
    }

    public void setApproved(AdminDashboardConsentFormSection approved) {
        this.approved = approved;
    }

    public AdminDashboardConsentFormSection getRejected() {
        return rejected;
    }

    public void setRejected(AdminDashboardConsentFormSection rejected) {
        this.rejected = rejected;
    }

    public AdminDashboardConsentFormSection getNotSubmitted() {
        return notSubmitted;
    }

    public void setNotSubmitted(AdminDashboardConsentFormSection notSubmitted) {
        this.notSubmitted = notSubmitted;
    }
}
