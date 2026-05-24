package com.petra.final_exam_work.dto.requestDto.contributor.editUploadedPhotos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class EditCoverPhotoRequest {

    @NotNull
    private UUID coverPhotoPublicUuid;

    public EditCoverPhotoRequest() {
    }

    public EditCoverPhotoRequest(UUID coverPhotoPublicUuid) {
        this.coverPhotoPublicUuid = coverPhotoPublicUuid;
    }

    public UUID getCoverPhotoPublicUuid() {
        return coverPhotoPublicUuid;
    }

    public void setCoverPhotoPublicUuid(UUID coverPhotoPublicUuid) {
        this.coverPhotoPublicUuid = coverPhotoPublicUuid;
    }
}
