package com.petra.final_exam_work.dto.responseDto.contributor.EditAlbum;

import com.petra.final_exam_work.dto.responseDto.contributor.ContributorUploadPhotos.CoverPhotoResponse;

public class EditCoverPhotoResponse {

    private CoverPhotoResponse coverPhoto;

    public EditCoverPhotoResponse() {
    }

    public EditCoverPhotoResponse(CoverPhotoResponse coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public CoverPhotoResponse getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(CoverPhotoResponse coverPhoto) {
        this.coverPhoto = coverPhoto;
    }
}
