package com.petra.final_exam_work.entity.junktionTables.photoAlbumPhoto;

import com.petra.final_exam_work.entity.photo.Photo;
import com.petra.final_exam_work.entity.photo.PhotoAlbum;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "photo_albums_photos")
public class PhotoAlbumPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "photo_album_id", nullable = false)
    private PhotoAlbum photoAlbum;

    @ManyToOne
    @JoinColumn(name = "photo_id", nullable = false)
    private Photo photo;

    @Column(name = "position", nullable = false)
    private Integer position;

    @Column(name = "added_at", updatable = false, insertable = false)
    private Instant addedAt;

    public PhotoAlbumPhoto() {
    }

    public PhotoAlbumPhoto(Long id, PhotoAlbum photoAlbum, Photo photo, Integer position, Instant addedAt) {
        this.id = id;
        this.photoAlbum = photoAlbum;
        this.photo = photo;
        this.position = position;
        this.addedAt = addedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
