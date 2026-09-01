package com.petra.final_exam_work.entity;

import com.petra.final_exam_work.entity.photo.PhotoAlbum;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tags")
public class Tag {

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

    @Column(
            name = "name_tag",
            nullable = false
    )
    private String nameTag;

    @Column(
            name = "normalized_tag",
            nullable = false,
            unique = true
    )
    private String normalizedTag;

    @ManyToMany
    @JoinTable(
            name = "photo_albums_tags",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "photo_album_id")
    )
    private Set<PhotoAlbum> photoAlbums = new HashSet<>();

    @PrePersist
    private void prePersist() {
        if (publicUuid == null) {
            publicUuid = UUID.randomUUID();
        }
    }

    public Tag() {
    }

    public Tag(Long id, UUID publicUuid, String nameTag, String normalizedTag, Set<PhotoAlbum> photoAlbums) {
        this.id = id;
        this.publicUuid = publicUuid;
        this.nameTag = nameTag;
        this.normalizedTag = normalizedTag;
        this.photoAlbums = photoAlbums;
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

    public String getNameTag() {
        return nameTag;
    }

    public void setNameTag(String nameTag) {
        this.nameTag = nameTag;
    }

    public String getNormalizedTag() {
        return normalizedTag;
    }

    public void setNormalizedTag(String normalizedTag) {
        this.normalizedTag = normalizedTag;
    }

    public Set<PhotoAlbum> getPhotoAlbums() {
        return photoAlbums;
    }

    public void setPhotoAlbums(Set<PhotoAlbum> photoAlbums) {
        this.photoAlbums = photoAlbums;
    }
}
