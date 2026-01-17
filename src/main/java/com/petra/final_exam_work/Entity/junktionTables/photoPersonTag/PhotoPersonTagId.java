package com.petra.final_exam_work.Entity.junktionTables.photoPersonTag;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PhotoPersonTagId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "photo_id")
    private Long photoId;

    public PhotoPersonTagId() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PhotoPersonTagId that)) return false;
        return Objects.equals(userId, that.userId) && Objects.equals(photoId, that.photoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, photoId);
    }
}
