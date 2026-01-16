package com.petra.final_exam_work.Entity.junktionTables;

import com.petra.final_exam_work.Entity.consentForm.ConsentForm;
import com.petra.final_exam_work.Entity.photo.AlbumRoleStatus;
import com.petra.final_exam_work.Entity.photo.PhotoAlbum;
import com.petra.final_exam_work.Entity.user.User;
import com.petra.final_exam_work.config.AlbumRoleStatusConverter;
import jakarta.persistence.*;

@Entity
@Table(name = "users_photo_albums")
public class UserPhotoAlbum {

    @EmbeddedId
    private UserPhotoAlbumId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("photoAlbumId")
    @JoinColumn(name = "photo_albums_id")
    private PhotoAlbum photoAlbum;

    @Convert(converter = AlbumRoleStatusConverter.class)
    @Column(name = "album_role_status", nullable = false)
    private AlbumRoleStatus albumRoleStatus;

    public UserPhotoAlbum() {
    }

    public UserPhotoAlbumId getId() {
        return id;
    }

    public void setId(UserPhotoAlbumId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PhotoAlbum getPhotoAlbum() {
        return photoAlbum;
    }

    public void setPhotoAlbum(PhotoAlbum photoAlbum) {
        this.photoAlbum = photoAlbum;
    }

    public AlbumRoleStatus getAlbumRoleStatus() {
        return albumRoleStatus;
    }

    public void setAlbumRoleStatus(AlbumRoleStatus albumRoleStatus) {
        this.albumRoleStatus = albumRoleStatus;
    }
}
