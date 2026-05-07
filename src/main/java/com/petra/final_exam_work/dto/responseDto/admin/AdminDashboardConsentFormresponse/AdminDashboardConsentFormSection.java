package com.petra.final_exam_work.dto.responseDto.admin.AdminDashboardConsentFormresponse;

import java.util.List;

public class AdminDashboardConsentFormSection {
    private long total;
    private List<AdminDashboardConsentFormItem> latest;

    public AdminDashboardConsentFormSection() {
    }

    public AdminDashboardConsentFormSection(long total, List<AdminDashboardConsentFormItem> latest) {
        this.total = total;
        this.latest = latest;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<AdminDashboardConsentFormItem> getLatest() {
        return latest;
    }

    public void setLatest(List<AdminDashboardConsentFormItem> latest) {
        this.latest = latest;
    }
}
