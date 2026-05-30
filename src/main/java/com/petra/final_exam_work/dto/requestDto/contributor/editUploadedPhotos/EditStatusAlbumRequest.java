package com.petra.final_exam_work.dto.requestDto.contributor.editUploadedPhotos;

import com.petra.final_exam_work.entity.enums.ContentStatus;

public class EditStatusAlbumRequest {

    private ContentStatus status;

    public EditStatusAlbumRequest() {
    }

    public EditStatusAlbumRequest(ContentStatus status) {
        this.status = status;
    }

    public ContentStatus getStatus() {
        return status;
    }

    public void setStatus(ContentStatus status) {
        this.status = status;
    }
}
