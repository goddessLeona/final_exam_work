package com.petra.final_exam_work.dto.responseDto;

import java.util.List;

public class LoginResponse {

    private List<String> roles;

    public LoginResponse(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getRoles() {
        return roles;
    }
}
