package com.petra.final_exam_work.dto.responseDto.contributor.ContributorUploadPhotos;

import java.util.UUID;

public class AddTagResponse {

    private UUID publicUuid;
    private String nameTag;

    public AddTagResponse() {
    }

    public AddTagResponse(UUID publicUuid, String nameTag) {
        this.publicUuid = publicUuid;
        this.nameTag = nameTag;
    }

    public UUID getPublicUuid() {
        return publicUuid;
    }

    public void setPublicUuid(UUID publicUuid) {
        this.publicUuid = publicUuid;
    }

    public String getNameTag() {
        return nameTag;
    }

    public void setNameTag(String nameTag) {
        this.nameTag = nameTag;
    }
}
