package com.petra.final_exam_work.dto.responseDto.contributor;

import com.petra.final_exam_work.entity.enums.ContentStatus;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class UploadContentResponse {

    private UUID albumUuid;

    private String photoAlbumName;
    private String description;
    private Instant publishedAt;
    private ContentStatus contentStatus;

    private String username;
    private List<String> photoUrls;

    public UploadContentResponse() {
    }

    public UploadContentResponse(UUID albumUuid, String photoAlbumName, String description, Instant publishedAt,
                                 ContentStatus contentStatus, String username, List<String> photoUrls) {
        this.albumUuid = albumUuid;
        this.photoAlbumName = photoAlbumName;
        this.description = description;
        this.publishedAt = publishedAt;
        this.contentStatus = contentStatus;
        this.username = username;
        this.photoUrls = photoUrls;
    }

    public UUID getAlbumUuid() {
        return albumUuid;
    }

    public void setAlbumUuid(UUID albumUuid) {
        this.albumUuid = albumUuid;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }
}
