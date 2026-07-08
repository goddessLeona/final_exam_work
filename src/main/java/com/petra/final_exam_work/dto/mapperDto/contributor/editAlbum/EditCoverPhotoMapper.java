package com.petra.final_exam_work.dto.mapperDto.contributor.editAlbum;

import com.petra.final_exam_work.dto.responseDto.contributor.ContributorUploadPhotos.CoverPhotoResponse;
import com.petra.final_exam_work.dto.responseDto.contributor.EditAlbum.EditCoverPhotoResponse;
import com.petra.final_exam_work.entity.photo.Photo;
import com.petra.final_exam_work.entity.photo.PhotoAlbum;
import org.springframework.stereotype.Component;

@Component
public class EditCoverPhotoMapper {

    public EditCoverPhotoResponse toDto(PhotoAlbum photoAlbum) {

        return new EditCoverPhotoResponse(
            map(photoAlbum.getCoverPhoto())
        );
    }

    private CoverPhotoResponse map(Photo photo) {

        if (photo == null) {
            return null;
        }

        CoverPhotoResponse response = new CoverPhotoResponse();
        response.setPublicUuid(photo.getPublicUuid());
        response.setCoverPhotoUrl(photo.getMediumPath());

        return response;
    }
}
