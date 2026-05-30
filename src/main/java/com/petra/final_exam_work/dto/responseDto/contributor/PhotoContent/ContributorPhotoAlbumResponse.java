package com.petra.final_exam_work.dto.responseDto.contributor.PhotoContent;

import com.petra.final_exam_work.dto.responseDto.contributor.ContributorUploadPhotos.CoverPhotoResponse;
import com.petra.final_exam_work.entity.enums.ContentStatus;
import com.petra.final_exam_work.entity.enums.ContentType;

import java.time.Instant;
import java.util.UUID;

public class ContributorPhotoAlbumResponse {

    private UUID publicUuid;
    private String photoAlbumName;
    private Instant publishedAt;

    private ContentType contentType;
    private ContentStatus contentStatus;
    private CoverPhotoResponse coverPhoto;

    public ContributorPhotoAlbumResponse() {
    }

    public ContributorPhotoAlbumResponse(UUID publicUuid, String photoAlbumName, Instant publishedAt,
                                         ContentType contentType, ContentStatus contentStatus, CoverPhotoResponse coverPhoto) {
        this.publicUuid = publicUuid;
        this.photoAlbumName = photoAlbumName;
        this.publishedAt = publishedAt;
        this.contentType = contentType;
        this.contentStatus = contentStatus;
        this.coverPhoto = coverPhoto;
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
