package com.petra.final_exam_work.dto.requestDto.contributor;

import com.petra.final_exam_work.entity.enums.ContentStatus;
import com.petra.final_exam_work.entity.enums.ContentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;

public class UploadPhotoContentRequest {

    @NotBlank
    @Size(min = 1, max = 20, message = "Can only be 20 characters long")
    private String photoAlbumName;

    @NotBlank
    @Size(min = 1, max = 50, message = "Can only be 50 characters long")
    private String description;

    private Instant publishedAt;
    private ContentType contentType;

    private List<MultipartFile> photos;

    public UploadPhotoContentRequest() {
    }

    public UploadPhotoContentRequest(String photoAlbumName, String description, Instant publishedAt,
                                     ContentType contentType, List<MultipartFile> photos) {
        this.photoAlbumName = photoAlbumName;
        this.description = description;
        this.publishedAt = publishedAt;
        this.contentType = contentType;
        this.photos = photos;
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

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public List<MultipartFile> getPhotos() {
        return photos;
    }

    public void setPhotos(List<MultipartFile> photos) {
        this.photos = photos;
    }
}
