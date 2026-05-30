package com.petra.final_exam_work.dto.requestDto.contributor.editUploadedPhotos;

import java.time.Instant;

public class EditPublishedDateRequest {

    private Instant publishedAt;

    public EditPublishedDateRequest() {
    }

    public EditPublishedDateRequest(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }
}
