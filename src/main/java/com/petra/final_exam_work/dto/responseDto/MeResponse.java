package com.petra.final_exam_work.dto.responseDto;

public class MeResponse {

    private String username;

    public MeResponse() {
    }

    public MeResponse(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
