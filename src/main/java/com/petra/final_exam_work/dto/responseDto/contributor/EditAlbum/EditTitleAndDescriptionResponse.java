package com.petra.final_exam_work.dto.responseDto.contributor.EditAlbum;

public class EditTitleAndDescriptionResponse {

    private String photoAlbumName;
    private String description;

    public EditTitleAndDescriptionResponse() {
    }

    public EditTitleAndDescriptionResponse(String photoAlbumName, String description) {
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
