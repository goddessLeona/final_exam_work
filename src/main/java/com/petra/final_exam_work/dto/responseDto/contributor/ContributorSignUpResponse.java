package com.petra.final_exam_work.dto.responseDto.contributor;

public class ContributorSignUpResponse {

    private String username;

    public ContributorSignUpResponse() {
    }

    public ContributorSignUpResponse(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
