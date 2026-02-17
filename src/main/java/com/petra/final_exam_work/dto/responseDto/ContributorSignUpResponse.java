package com.petra.final_exam_work.dto.responseDto;

import com.petra.final_exam_work.entity.user.Role;

import java.util.UUID;

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
