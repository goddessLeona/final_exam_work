package com.petra.final_exam_work.dto.responseDto;

public class ContributorWelcomeResponse {

    private boolean contributor;
    private String message;

    public ContributorWelcomeResponse() {
    }

    public ContributorWelcomeResponse(boolean contributor, String message) {
        this.contributor = contributor;
        this.message = message;
    }

    public boolean isContributor() {
        return contributor;
    }

    public void setContributor(boolean contributor) {
        this.contributor = contributor;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
