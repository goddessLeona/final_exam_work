package com.petra.final_exam_work.dto.responseDto.contributor.ContributorUploadPhotos;

import java.util.UUID;

public class CoverPhotoResponse {

    private UUID publicUuid;
    private String coverPhotoUrl;

    public CoverPhotoResponse() {
    }

    public CoverPhotoResponse(UUID publicUuid, String coverPhotoUrl) {
        this.publicUuid = publicUuid;
        this.coverPhotoUrl = coverPhotoUrl;
    }

    public UUID getPublicUuid() {
        return publicUuid;
    }

    public void setPublicUuid(UUID publicUuid) {
        this.publicUuid = publicUuid;
    }

    public String getCoverPhotoUrl() {
        return coverPhotoUrl;
    }

    public void setCoverPhotoUrl(String coverPhotoUrl) {
        this.coverPhotoUrl = coverPhotoUrl;
    }
}
