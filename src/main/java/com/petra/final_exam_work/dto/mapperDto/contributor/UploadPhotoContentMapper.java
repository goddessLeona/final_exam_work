package com.petra.final_exam_work.dto.mapperDto.contributor;

import com.petra.final_exam_work.dto.responseDto.contributor.ContributorUploadPhotos.CoverPhotoResponse;
import com.petra.final_exam_work.dto.responseDto.contributor.ContributorUploadPhotos.UploadContentResponse;
import com.petra.final_exam_work.entity.photo.Photo;
import com.petra.final_exam_work.entity.photo.PhotoAlbum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UploadPhotoContentMapper {

    @Mapping(target = "albumUuid", source = "photoAlbum.publicUuid")
    @Mapping(target = "photoAlbumName", source = "photoAlbum.photoAlbumName")
    @Mapping(target = "description", source = "photoAlbum.description" )
    @Mapping(target = "publishedAt", source = "photoAlbum.publishedAt")
    @Mapping(target = "username", source = "photoAlbum.ownedByUser.username")
    @Mapping(target = "photoUrls", source = "photos" )
    @Mapping(target = "coverPhoto", source = "photoAlbum.coverPhoto")
    UploadContentResponse toResponse (
        PhotoAlbum photoAlbum,
        List<Photo> photos
    );

    default List<String> mapPhotoUrls(List<Photo> photos) {
        return photos.stream()
                .map(Photo::getMediumPath)
                .toList();
    }

    default CoverPhotoResponse map(Photo photo) {

        if (photo == null) {
            return null;
        }

        CoverPhotoResponse response = new CoverPhotoResponse();
        response.setPublicUuid(photo.getPublicUuid());
        response.setCoverPhotoUrl(photo.getMediumPath());

        return response;
    }
}
