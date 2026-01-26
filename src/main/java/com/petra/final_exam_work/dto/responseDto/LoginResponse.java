package com.petra.final_exam_work.dto.responseDto;

import java.util.List;

public class LoginResponse {

    private String token;
    private List<String> roles;

    public LoginResponse(String token, List<String> roles) {
        this.token = token;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public List<String> getRoles() {
        return roles;
    }
}
