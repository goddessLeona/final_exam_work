package com.petra.final_exam_work.dto.responseDto.members;

import java.util.UUID;

public class PhotoResponse {

    private UUID publicUuid;
    private String photoUrl;

    public PhotoResponse() {
    }

    public PhotoResponse(UUID publicUuid, String photoUrl) {
        this.publicUuid = publicUuid;
        this.photoUrl = photoUrl;
    }

    public UUID getPublicUuid() {
        return publicUuid;
    }

    public void setPublicUuid(UUID publicUuid) {
        this.publicUuid = publicUuid;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
