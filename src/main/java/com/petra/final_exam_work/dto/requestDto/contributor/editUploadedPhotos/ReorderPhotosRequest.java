package com.petra.final_exam_work.dto.requestDto.contributor.editUploadedPhotos;

import java.util.UUID;

public class ReorderPhotosRequest {

    private UUID photoPublicUuid;
    private int targetPosition;

    public ReorderPhotosRequest() {
    }

    public ReorderPhotosRequest(UUID photoPublicUuid, int targetPosition) {
        this.photoPublicUuid = photoPublicUuid;
        this.targetPosition = targetPosition;
    }

    public UUID getPhotoPublicUuid() {
        return photoPublicUuid;
    }

    public void setPhotoPublicUuid(UUID photoPublicUuid) {
        this.photoPublicUuid = photoPublicUuid;
    }

    public int getTargetPosition() {
        return targetPosition;
    }

    public void setTargetPosition(int targetPosition) {
        this.targetPosition = targetPosition;
    }
}
