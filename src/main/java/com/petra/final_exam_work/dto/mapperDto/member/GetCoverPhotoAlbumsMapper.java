package com.petra.final_exam_work.dto.mapperDto.member;

import com.petra.final_exam_work.dto.responseDto.contributor.ContributorUploadPhotos.CoverPhotoResponse;
import com.petra.final_exam_work.dto.responseDto.members.GetCoverPhotoAlbumsResponse;
import com.petra.final_exam_work.entity.photo.Photo;
import com.petra.final_exam_work.entity.photo.PhotoAlbum;

public class GetCoverPhotoAlbumsMapper {

    public GetCoverPhotoAlbumsResponse toDto (PhotoAlbum photoAlbum) {

        return new GetCoverPhotoAlbumsResponse(
                photoAlbum.getPublicUuid(),
                photoAlbum.getPhotoAlbumName(),
                map(photoAlbum.getCoverPhoto())
        );

    }

    private CoverPhotoResponse map(Photo photo) {

        if (photo == null) {
            return null;
        }

        CoverPhotoResponse response = new CoverPhotoResponse();
        response.setPublicUuid(photo.getPublicUuid());
        response.setCoverPhotoUrl(photo.getPhotoFilePath());

        return response;
    }
}
