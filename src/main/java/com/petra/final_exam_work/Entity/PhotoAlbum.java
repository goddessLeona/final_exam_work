package com.petra.final_exam_work.Entity;

import com.petra.final_exam_work.Entity.Enum.ConsentStatus;
import com.petra.final_exam_work.Entity.Enum.ContentStatus;
import com.petra.final_exam_work.config.ContentStatusConverter;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "photo_albums")
public class PhotoAlbum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "public_uuid",
            updatable = false,
            unique = true,
            nullable = false
    )
    private UUID publicUuid;

    @Column(name = "photo_album_name", nullable = false, unique = true)
    private String photoAlbumName;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "published_date", nullable = false)
    private Instant publishedDate;

    @Convert(converter = ContentStatusConverter.class)
    @Column(name = "status", nullable = false)
    private ContentStatus contentStatus;

    @Column(name = "rules_check", nullable = false)
    private Boolean rulesCheck;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_user_id", nullable = false)
    private User ownedByUser;

    public PhotoAlbum() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getPublicUuid() {
        return publicUuid;
    }

    public void setPublicUuid(UUID publicUuid) {
        this.publicUuid = publicUuid;
    }

    public String getPhotoAlbumName() {
        return photoAlbumName;
    }

    public void setPhotoAlbumName(String photoAlbumName) {
        this.photoAlbumName = photoAlbumName;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Instant publishedDate) {
        this.publishedDate = publishedDate;
    }

    public ContentStatus getContentStatus() {
        return contentStatus;
    }

    public void setContentStatus(ContentStatus contentStatus) {
        this.contentStatus = contentStatus;
    }

    public Boolean getRulesCheck() {
        return rulesCheck;
    }

    public void setRulesCheck(Boolean rulesCheck) {
        this.rulesCheck = rulesCheck;
    }

    public User getOwnedByUser() {
        return ownedByUser;
    }

    public void setOwnedByUser(User ownedByUser) {
        this.ownedByUser = ownedByUser;
    }
}
