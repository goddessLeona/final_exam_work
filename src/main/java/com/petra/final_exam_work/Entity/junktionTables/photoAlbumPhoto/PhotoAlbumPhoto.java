package com.petra.final_exam_work.Entity.junktionTables.photoAlbumPhoto;

import com.petra.final_exam_work.Entity.photo.Photo;
import com.petra.final_exam_work.Entity.photo.PhotoAlbum;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "photo_albums_photos")
public class PhotoAlbumPhoto {

    @EmbeddedId
    private PhotoAlbumPhotoId id;

    @ManyToOne
    @MapsId("photoAlbumId")
    @JoinColumn(name = "photo_album_id")
    private PhotoAlbum photoAlbum;

    @ManyToOne
    @MapsId("photoId")
    @JoinColumn(name = "photo_id")
    private Photo photo;

    @Column(name = "position", nullable = false)
    private Integer position;

    @Column(name = "added_at", updatable = false, insertable = false)
    private Instant addedAt;

    public PhotoAlbumPhoto() {
    }

    public PhotoAlbumPhotoId getId() {
        return id;
    }

    public void setId(PhotoAlbumPhotoId id) {
        this.id = id;
    }

    public PhotoAlbum getPhotoAlbum() {
        return photoAlbum;
    }

    public void setPhotoAlbum(PhotoAlbum photoAlbum) {
        this.photoAlbum = photoAlbum;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Instant getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Instant addedAt) {
        this.addedAt = addedAt;
    }
}
