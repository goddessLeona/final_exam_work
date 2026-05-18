package com.petra.final_exam_work.dto.mapperDto.contributor;

import com.petra.final_exam_work.dto.responseDto.contributor.ContributorUploadPhotos.CoverPhotoResponse;
import com.petra.final_exam_work.dto.responseDto.contributor.PhotoContent.ContributorPhotoAlbumResponse;
import com.petra.final_exam_work.entity.photo.Photo;
import com.petra.final_exam_work.entity.photo.PhotoAlbum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContributorPhotoAlbumMapper {

    @Mapping(target = "albumUuid", source = "publicUuid")
    @Mapping(target = "coverPhoto", source = "coverPhoto")

    ContributorPhotoAlbumResponse toResponse (
            PhotoAlbum photoAlbum
    );

    default CoverPhotoResponse map(Photo photo) {

        if (photo == null) {
            return null;
        }

        CoverPhotoResponse response = new CoverPhotoResponse();

        response.setPublicUuid(photo.getPublicUuid());
        response.setCoverPhotoUrl(photo.getPhotoFilePath());

        return response;
    }

}
