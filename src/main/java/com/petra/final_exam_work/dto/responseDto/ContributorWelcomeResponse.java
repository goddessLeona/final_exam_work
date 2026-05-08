package com.petra.final_exam_work.dto.responseDto;

import com.petra.final_exam_work.entity.enums.ContributorStatus;

public class ContributorWelcomeResponse {

    private ContributorStatus status;
    private String message;

    public ContributorWelcomeResponse() {
    }

    public ContributorWelcomeResponse(ContributorStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public ContributorStatus getStatus() {
        return status;
    }

    public void setStatus(ContributorStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
