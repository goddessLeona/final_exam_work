package com.petra.final_exam_work.dto.mapperDto.contributor;

import com.petra.final_exam_work.dto.responseDto.contributor.UploadPhotoContentResponse;
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
    @Mapping(target = "publishedAt", source = "photoAlbum.publishedDate")
    @Mapping(target = "contentStatus", source = "photoAlbum.contentStatus")
    @Mapping(target = "username", source = "photoAlbum.ownedByUser")
    @Mapping(target = "photoUrls", source = "photos" )
    UploadPhotoContentResponse toResponse (
        PhotoAlbum photoAlbum,
        List<Photo> photos
    );

    default List<String> mapPhotoUrls(List<Photo> photos) {
        return photos.stream()
                .map(Photo::getPhotoFilePath)
                .toList();
    }
}
