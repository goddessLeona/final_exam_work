package com.petra.final_exam_work.dto.responseDto.members;

import com.petra.final_exam_work.dto.responseDto.contributor.ContributorUploadPhotos.CoverPhotoResponse;

import java.util.UUID;

public class GetCoverPhotoAlbumsResponse {

    private UUID publicUuid;
    private String photoAlbumName;
    private CoverPhotoResponse coverPhoto;

    public GetCoverPhotoAlbumsResponse() {
    }

    public GetCoverPhotoAlbumsResponse(UUID publicUuid, String photoAlbumName, CoverPhotoResponse coverPhoto) {
        this.publicUuid = publicUuid;
        this.photoAlbumName = photoAlbumName;
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

    public CoverPhotoResponse getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(CoverPhotoResponse coverPhoto) {
        this.coverPhoto = coverPhoto;
    }
}
