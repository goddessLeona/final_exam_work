package com.petra.final_exam_work.dto.responseDto.members;

import com.petra.final_exam_work.dto.responseDto.contributor.ContributorUploadPhotos.CoverPhotoResponse;
import com.petra.final_exam_work.entity.enums.ContentStatus;

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
    private List<PhotoResponse> photos ;
    private CoverPhotoResponse coverPhoto;
    private ContentStatus contentStatus;

    public GetPhotoAlbumsResponse() {
    }

    public GetPhotoAlbumsResponse(UUID publicUuid, String photoAlbumName, String description, String username,
                                  Instant publishedAt, List<AlbumTagResponse> albumTags, List<PhotoResponse> photos,
                                  CoverPhotoResponse coverPhoto, ContentStatus contentStatus) {
        this.publicUuid = publicUuid;
        this.photoAlbumName = photoAlbumName;
        this.description = description;
        this.username = username;
        this.publishedAt = publishedAt;
        this.albumTags = albumTags;
        this.photos = photos;
        this.coverPhoto = coverPhoto;
        this.contentStatus = contentStatus;
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

    public List<PhotoResponse> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoResponse> photos) {
        this.photos = photos;
    }

    public CoverPhotoResponse getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(CoverPhotoResponse coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public ContentStatus getContentStatus() {
        return contentStatus;
    }

    public void setContentStatus(ContentStatus contentStatus) {
        this.contentStatus = contentStatus;
    }
}
