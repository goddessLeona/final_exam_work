package com.petra.final_exam_work.entity.junktionTables.photoPersonTag;

import com.petra.final_exam_work.entity.photo.Photo;
import com.petra.final_exam_work.entity.user.User;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "photo_person_tags")
public class PhotoPersonTag {

    @EmbeddedId
    private PhotoPersonTagId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("photoId")
    @JoinColumn(name = "photo_id")
    private Photo photo;

    @Column(name = "can_remove", nullable = false)
    private Boolean canRemove = true;

    @Column(name = "tagged_at", updatable = false, insertable = false)
    private Instant taggedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tagged_by_user_id", nullable = false)
    private User taggedByUser;

    public PhotoPersonTag() {
    }

    public PhotoPersonTagId getId() {
        return id;
    }

    public void setId(PhotoPersonTagId id) {
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

    public Boolean getCanRemove() {
        return canRemove;
    }

    public void setCanRemove(Boolean canRemove) {
        this.canRemove = canRemove;
    }

    public Instant getTaggedAt() {
        return taggedAt;
    }

    public void setTaggedAt(Instant taggedAt) {
        this.taggedAt = taggedAt;
    }

    public User getTaggedByUser() {
        return taggedByUser;
    }

    public void setTaggedByUser(User taggedByUser) {
        this.taggedByUser = taggedByUser;
    }
}
