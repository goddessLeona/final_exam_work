package com.petra.final_exam_work.Entity;

import com.petra.final_exam_work.Entity.photo.PhotoAlbum;
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
            nullable = false,
            unique = true,
            columnDefinition = "citext"
    )
    private String nameTag;

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
}
