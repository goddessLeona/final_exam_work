package com.petra.final_exam_work.dto.requestDto.contributor.editUploadedPhotos;

import java.time.Instant;

public class EditPublishedDate {

    private Instant publishedAt;

    public EditPublishedDate() {
    }

    public EditPublishedDate(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }
}
