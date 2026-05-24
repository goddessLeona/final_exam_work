package com.petra.final_exam_work.entity.photo;

import com.petra.final_exam_work.entity.Tag;
import com.petra.final_exam_work.entity.enums.ContentStatus;
import com.petra.final_exam_work.entity.enums.ContentType;
import com.petra.final_exam_work.entity.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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

    @Column(name= "description", nullable = false)
    private String description;

    @Column(name = "created_at", updatable = false, insertable = false)
    private Instant createdAt;

    @Column(name = "published_at", updatable = true)
    private Instant publishedAt;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "content_status", columnDefinition = "content_status", nullable = false)
    private ContentStatus contentStatus = ContentStatus.DRAFT;

    @Column(name = "rules_verified", nullable = false)
    private Boolean rulesVerified;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_user_id", nullable = false)
    private User ownedByUser;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name= "content_type", columnDefinition = "content_type", nullable = false)
    private ContentType contentType = ContentType.PHOTO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cover_photo_id")
    private Photo coverPhoto;

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

    public PhotoAlbum(Long id, UUID publicUuid, String photoAlbumName, String description, Instant createdAt,
                      Instant publishedAt, ContentStatus contentStatus, Boolean rulesVerified, User ownedByUser,
                      ContentType contentType, Photo coverPhoto, Set<Tag> tags) {
        this.id = id;
        this.publicUuid = publicUuid;
        this.photoAlbumName = photoAlbumName;
        this.description = description;
        this.createdAt = createdAt;
        this.publishedAt = publishedAt;
        this.contentStatus = contentStatus;
        this.rulesVerified = rulesVerified;
        this.ownedByUser = ownedByUser;
        this.contentType = contentType;
        this.coverPhoto = coverPhoto;
        this.tags = tags;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
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

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public Photo getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(Photo coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
