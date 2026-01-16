package com.petra.final_exam_work.Entity.junktionTables;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserPhotoAlbumId implements Serializable {

    private Long userId;
    private Long photoAlbumId;

    public UserPhotoAlbumId() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPhotoAlbumId() {
        return photoAlbumId;
    }

    public void setPhotoAlbumId(Long photoAlbumId) {
        this.photoAlbumId = photoAlbumId;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UserPhotoAlbumId that)) return false;
        return Objects.equals(userId, that.userId) && Objects.equals(photoAlbumId, that.photoAlbumId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, photoAlbumId);
    }
}
