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
}
