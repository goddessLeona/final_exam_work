package com.petra.final_exam_work.dto.responseDto.AdminDashboardConsentFormresponse;

public class AdminDashboardConsentFormResponse {

    private long total;

    private DashboardSection pending;
    private DashboardSection approved;
    private DashboardSection rejected;
    private DashboardSection notSubmitted;

    public AdminDashboardConsentFormResponse() {
    }

    public AdminDashboardConsentFormResponse(long total, DashboardSection pending, DashboardSection approved,
                                             DashboardSection rejected, DashboardSection notSubmitted) {
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

    public DashboardSection getPending() {
        return pending;
    }

    public void setPending(DashboardSection pending) {
        this.pending = pending;
    }

    public DashboardSection getApproved() {
        return approved;
    }

    public void setApproved(DashboardSection approved) {
        this.approved = approved;
    }

    public DashboardSection getRejected() {
        return rejected;
    }

    public void setRejected(DashboardSection rejected) {
        this.rejected = rejected;
    }

    public DashboardSection getNotSubmitted() {
        return notSubmitted;
    }

    public void setNotSubmitted(DashboardSection notSubmitted) {
        this.notSubmitted = notSubmitted;
    }
}
