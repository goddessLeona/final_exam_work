package com.petra.final_exam_work.dto.mapperDto.contributor.editAlbum;

import com.petra.final_exam_work.dto.requestDto.contributor.editUploadedPhotos.EditTitleAndDescriptionRequest;
import com.petra.final_exam_work.dto.responseDto.contributor.EditAlbum.EditTitleAndDescriptionResponse;
import com.petra.final_exam_work.entity.photo.PhotoAlbum;
import org.springframework.stereotype.Component;

@Component
public class EditTitleAndContributorMapper {

    public void updatePhotoAlbum(PhotoAlbum photoAlbum, EditTitleAndDescriptionRequest request){

        photoAlbum.setPhotoAlbumName(request.getPhotoAlbumName());
        photoAlbum.setDescription(request.getDescription());
    }

    public EditTitleAndDescriptionResponse toDto(PhotoAlbum photoAlbum) {

        return new EditTitleAndDescriptionResponse(
                photoAlbum.getPhotoAlbumName(),
                photoAlbum.getDescription()
        );
    }
}
