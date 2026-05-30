package com.petra.final_exam_work.dto.mapperDto.member;

import com.petra.final_exam_work.dto.responseDto.contributor.ContributorUploadPhotos.CoverPhotoResponse;
import com.petra.final_exam_work.dto.responseDto.members.AlbumTagResponse;
import com.petra.final_exam_work.dto.responseDto.members.GetPhotoAlbumsResponse;
import com.petra.final_exam_work.dto.responseDto.members.PhotoResponse;
import com.petra.final_exam_work.entity.Tag;
import com.petra.final_exam_work.entity.photo.Photo;
import com.petra.final_exam_work.entity.photo.PhotoAlbum;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class GetPhotoAlbumsMapper {

    public GetPhotoAlbumsResponse toDto(PhotoAlbum photoAlbum, List<Photo> photos) {

        return new GetPhotoAlbumsResponse(
                photoAlbum.getPublicUuid(),
                photoAlbum.getPhotoAlbumName(),
                photoAlbum.getDescription(),
                photoAlbum.getOwnedByUser().getUsername(),
                photoAlbum.getPublishedAt(),
                photoAlbum.getArchivedAt(),
                mapTags(photoAlbum.getTags()),
                mapPhotos(photos),
                map(photoAlbum.getCoverPhoto()),
                photoAlbum.getContentStatus()
        );
    }

    // ---------------- PHOTOS ----------------

    public List<PhotoResponse> mapPhotos(List<Photo> photos) {

        if (photos == null) {
            return List.of();
        }

        return photos.stream()
                .map(this::mapPhoto)
                .toList();
    }

    private PhotoResponse mapPhoto(Photo photo) {

        if (photo == null) {
            return null;
        }

        PhotoResponse response = new PhotoResponse();
        response.setPublicUuid(photo.getPublicUuid());
        response.setPhotoUrl(photo.getPhotoFilePath());

        return response;
    }

    // ---------------- TAGS ----------------

    public List<AlbumTagResponse> mapTags(Set<Tag> tags) {

        if (tags == null) {
            return List.of();
        }

        return tags.stream()
                .map(this::mapTag)
                .toList();
    }

    private AlbumTagResponse mapTag(Tag tag) {

        if (tag == null) {
            return null;
        }

        AlbumTagResponse response = new AlbumTagResponse();
        response.setPublicUuid(tag.getPublicUuid());
        response.setNameTag(tag.getNameTag());

        return response;
    }

    //----------- COVER PHOTO ---------
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
