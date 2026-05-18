package com.petra.final_exam_work.dto.responseDto.contributor.PhotoContent;

import com.petra.final_exam_work.dto.responseDto.contributor.ContributorUploadPhotos.CoverPhotoResponse;
import com.petra.final_exam_work.entity.enums.ContentStatus;
import com.petra.final_exam_work.entity.enums.ContentType;

import java.time.Instant;
import java.util.UUID;

public class ContributorPhotoAlbumResponse {

    private UUID albumUuid;
    private String photoAlbumName;
    private Instant publishedAt;

    private ContentType contentType;
    private ContentStatus contentStatus;
    private CoverPhotoResponse coverPhoto;

    public ContributorPhotoAlbumResponse() {
    }

    public ContributorPhotoAlbumResponse(UUID albumUuid, String photoAlbumName, Instant publishedAt,
                                         ContentType contentType, ContentStatus contentStatus, CoverPhotoResponse coverPhoto) {
        this.albumUuid = albumUuid;
        this.photoAlbumName = photoAlbumName;
        this.publishedAt = publishedAt;
        this.contentType = contentType;
        this.contentStatus = contentStatus;
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

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public ContentStatus getContentStatus() {
        return contentStatus;
    }

    public void setContentStatus(ContentStatus contentStatus) {
        this.contentStatus = contentStatus;
    }

    public CoverPhotoResponse getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(CoverPhotoResponse coverPhoto) {
        this.coverPhoto = coverPhoto;
    }
}
