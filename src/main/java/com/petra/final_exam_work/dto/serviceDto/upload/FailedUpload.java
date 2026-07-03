package com.petra.final_exam_work.dto.serviceDto.upload;

public class FailedUpload {

    private String fileName;
    private String reason;

    public FailedUpload() {
    }

    public FailedUpload(String fileName, String reason) {
        this.fileName = fileName;
        this.reason = reason;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
