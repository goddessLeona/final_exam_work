package com.petra.final_exam_work.dto.responseDto.AdminDashboardConsentFormresponse;

import java.util.List;

public class DashboardSection {
    private long total;
    private List<AdminConsentFormItem> latest;

    public DashboardSection() {
    }

    public DashboardSection(long total, List<AdminConsentFormItem> latest) {
        this.total = total;
        this.latest = latest;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<AdminConsentFormItem> getLatest() {
        return latest;
    }

    public void setLatest(List<AdminConsentFormItem> latest) {
        this.latest = latest;
    }
}
