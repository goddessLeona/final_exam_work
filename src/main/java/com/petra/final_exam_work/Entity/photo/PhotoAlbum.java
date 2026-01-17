package com.petra.final_exam_work.Entity.photo;

import com.petra.final_exam_work.Entity.Tag;
import com.petra.final_exam_work.Entity.user.User;
import com.petra.final_exam_work.config.ContentStatusConverter;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
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

    @Column(name = "photo_album_name", nullable = false)
    private String photoAlbumName;

    @Column(name = "created_at", updatable = false, insertable = false)
    private Instant createdAt;

    @Column(name = "published_at", updatable = true)
    private Instant publishedDate;

    @Convert(converter = ContentStatusConverter.class)
    @Column(name = "content_status", nullable = false)
    private ContentStatus contentStatus = ContentStatus.DRAFT;

    @Column(name = "rules_verified", nullable = false)
    private Boolean rulesVerified;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_user_id", nullable = false)
    private User ownedByUser;

    @ManyToMany(mappedBy = "photoAlbums")
    private Set<Tag> tags = new HashSet<>();

    @PrePersist
    private void prePersist() {
        if (publicUuid == null) {
            publicUuid = UUID.randomUUID();
        }
    }

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

    public Boolean getRulesVerified() {
        return rulesVerified;
    }

    public void setRulesVerified(Boolean rulesVerified) {
        this.rulesVerified = rulesVerified;
    }

    public User getOwnedByUser() {
        return ownedByUser;
    }

    public void setOwnedByUser(User ownedByUser) {
        this.ownedByUser = ownedByUser;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
