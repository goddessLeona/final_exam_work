package com.petra.final_exam_work.Entity.junktionTables.photoAlbumPhoto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PhotoAlbumPhotoId implements Serializable {

    @Column(name = "photo_album_id")
    private Long photoAlbumId;

    @Column(name = "photo_id")
    private Long photoId;

    public PhotoAlbumPhotoId() {
    }

    public Long getPhotoAlbumId() {
        return photoAlbumId;
    }

    public void setPhotoAlbumId(Long photoAlbumId) {
        this.photoAlbumId = photoAlbumId;
    }

    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PhotoAlbumPhotoId that)) return false;
        return Objects.equals(photoAlbumId, that.photoAlbumId) && Objects.equals(photoId, that.photoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(photoAlbumId, photoId);
    }
}
