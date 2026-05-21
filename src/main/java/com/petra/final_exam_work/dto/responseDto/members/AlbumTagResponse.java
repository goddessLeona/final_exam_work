package com.petra.final_exam_work.dto.responseDto.members;

import java.util.UUID;

public class AlbumTagResponse {

    private UUID publicUuid;
    private String nameTag;

    public AlbumTagResponse() {
    }

    public AlbumTagResponse(UUID publicUuid, String nameTag) {
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
