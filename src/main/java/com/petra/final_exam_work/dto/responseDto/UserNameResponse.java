package com.petra.final_exam_work.dto.responseDto;

public class UserNameResponse {

    private String username;

    public UserNameResponse() {
    }

    public UserNameResponse(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
