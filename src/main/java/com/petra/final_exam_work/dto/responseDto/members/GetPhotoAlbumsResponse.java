package com.petra.final_exam_work.dto.responseDto.members;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class GetPhotoAlbumsResponse {

    private UUID publicUuid;
    private String photoAlbumName;
    private String description;
    private String username;
    private Instant publishedAt;

    private List<AlbumTagResponse> albumTags;
    private List<PhotoResponse> photoUrl ;

    public GetPhotoAlbumsResponse() {
    }

    public GetPhotoAlbumsResponse(UUID publicUuid, String photoAlbumName, String description, String username,
                                  Instant publishedAt, List<AlbumTagResponse> albumTags, List<PhotoResponse> photoUrl) {
        this.publicUuid = publicUuid;
        this.photoAlbumName = photoAlbumName;
        this.description = description;
        this.username = username;
        this.publishedAt = publishedAt;
        this.albumTags = albumTags;
        this.photoUrl = photoUrl;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public List<AlbumTagResponse> getAlbumTags() {
        return albumTags;
    }

    public void setAlbumTags(List<AlbumTagResponse> albumTags) {
        this.albumTags = albumTags;
    }

    public List<PhotoResponse> getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(List<PhotoResponse> photoUrl) {
        this.photoUrl = photoUrl;
    }
}
