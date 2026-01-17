package com.petra.final_exam_work.Entity.junktionTables.photoContributor;

import com.petra.final_exam_work.Entity.photo.Photo;
import com.petra.final_exam_work.Entity.user.User;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "photo_contributors")
public class PhotoContributor {

    @EmbeddedId
    private PhotoContributorId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("photoId")
    @JoinColumn(name = "photo_id")
    private Photo photo;

    @Column(name = "added_at", updatable = false, insertable = false)
    private Instant addedAt;

    public PhotoContributor() {
    }

    public PhotoContributorId getId() {
        return id;
    }

    public void setId(PhotoContributorId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public Instant getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Instant addedAt) {
        this.addedAt = addedAt;
    }
}
