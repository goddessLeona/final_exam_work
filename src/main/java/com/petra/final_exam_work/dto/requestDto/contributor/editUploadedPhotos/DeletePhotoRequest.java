package com.petra.final_exam_work.dto.requestDto.contributor.editUploadedPhotos;

import java.util.UUID;

public class DeletePhotoRequest {

    private UUID photoPublicUuid;

    public DeletePhotoRequest() {
    }

    public DeletePhotoRequest(UUID photoPublicUuid) {
        this.photoPublicUuid = photoPublicUuid;
    }

    public UUID getPhotoPublicUuid() {
        return photoPublicUuid;
    }

    public void setPhotoPublicUuid(UUID photoPublicUuid) {
        this.photoPublicUuid = photoPublicUuid;
    }
}
