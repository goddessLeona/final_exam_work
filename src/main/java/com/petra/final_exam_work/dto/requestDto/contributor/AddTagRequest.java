package com.petra.final_exam_work.dto.requestDto.contributor;

import jakarta.validation.constraints.NotBlank;

public class AddTagRequest {

    @NotBlank(message = "Add at leased one tag to your album")
    private String nameTag;

    public AddTagRequest(String nameTag) {
        this.nameTag = nameTag;
    }

    public String getNameTag() {
        return nameTag;
    }

    public void setNameTag(String nameTag) {
        this.nameTag = nameTag;
    }
}
