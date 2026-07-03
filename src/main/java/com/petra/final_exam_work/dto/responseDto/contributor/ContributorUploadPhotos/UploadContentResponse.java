package com.petra.final_exam_work.dto.responseDto.contributor.ContributorUploadPhotos;

import com.petra.final_exam_work.entity.enums.ContentStatus;
import com.petra.final_exam_work.entity.enums.ContentType;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class UploadContentResponse {

    private UUID albumUuid;
    private String photoAlbumName;
    private String description;
    private Instant publishedAt;
    private ContentStatus contentStatus;
    private ContentType contentType;
    private String username;
    private List<String> photoUrls;
    private CoverPhotoResponse coverPhoto;

    public UploadContentResponse() {
    }

    public UploadContentResponse(UUID albumUuid, String photoAlbumName, String description, Instant publishedAt,
                                 ContentStatus contentStatus, ContentType contentType, String username,
                                 List<String> photoUrls, CoverPhotoResponse coverPhoto) {
        this.albumUuid = albumUuid;
        this.photoAlbumName = photoAlbumName;
        this.description = description;
        this.publishedAt = publishedAt;
        this.contentStatus = contentStatus;
        this.contentType = contentType;
        this.username = username;
        this.photoUrls = photoUrls;
        this.coverPhoto = coverPhoto;
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

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
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

    public CoverPhotoResponse getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(CoverPhotoResponse coverPhoto) {
        this.coverPhoto = coverPhoto;
    }
}
