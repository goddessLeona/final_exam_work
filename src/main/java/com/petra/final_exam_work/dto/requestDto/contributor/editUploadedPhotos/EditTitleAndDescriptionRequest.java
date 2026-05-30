package com.petra.final_exam_work.dto.requestDto.contributor.editUploadedPhotos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EditTitleAndDescriptionRequest {

    @NotBlank
    @Size(min = 1, max = 20, message = "Can only be 20 characters long")
    private String photoAlbumName;

    @NotBlank
    @Size(min = 1, max = 50, message = "Can only be 50 characters long")
    private String description;

    public EditTitleAndDescriptionRequest() {
    }

    public EditTitleAndDescriptionRequest(String photoAlbumName, String description) {
        this.photoAlbumName = photoAlbumName;
        this.description = description;
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
}
