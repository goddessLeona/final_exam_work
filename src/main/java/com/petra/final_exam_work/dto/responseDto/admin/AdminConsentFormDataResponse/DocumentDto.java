package com.petra.final_exam_work.dto.responseDto.admin.AdminConsentFormDataResponse;

import com.petra.final_exam_work.entity.enums.ReviewStatus;

public class DocumentDto {

    private String filePath;
    private ReviewStatus status;

    public DocumentDto() {
    }

    public DocumentDto(String filePath, ReviewStatus status) {
        this.filePath = filePath;
        this.status = status;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public ReviewStatus getStatus() {
        return status;
    }

    public void setStatus(ReviewStatus status) {
        this.status = status;
    }
}
