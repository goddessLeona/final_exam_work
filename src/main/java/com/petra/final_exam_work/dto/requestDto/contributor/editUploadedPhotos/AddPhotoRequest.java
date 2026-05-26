package com.petra.final_exam_work.dto.requestDto.contributor.editUploadedPhotos;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class AddPhotoRequest {

    private List<MultipartFile> photos;

    public AddPhotoRequest() {
    }

    public AddPhotoRequest(List<MultipartFile> photos) {
        this.photos = photos;
    }

    public List<MultipartFile> getPhotos() {
        return photos;
    }

    public void setPhotos(List<MultipartFile> photos) {
        this.photos = photos;
    }
}
