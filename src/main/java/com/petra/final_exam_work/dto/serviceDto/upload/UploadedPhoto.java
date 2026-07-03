package com.petra.final_exam_work.dto.serviceDto.upload;

import org.springframework.web.multipart.MultipartFile;

public class UploadedPhoto {

    private MultipartFile originalFile;

    private String thumbnailPath;
    private String mediumPath;
    private String largePath;

    private Integer width;
    private Integer height;
    private long sizeBytes;
    private String mimeType;
    private String fileName;

    public UploadedPhoto() {
    }

    public UploadedPhoto(MultipartFile originalFile, String thumbnailPath, String mediumPath, String largePath,
                         Integer width, Integer height, long sizeBytes, String mimeType, String fileName) {
        this.originalFile = originalFile;
        this.thumbnailPath = thumbnailPath;
        this.mediumPath = mediumPath;
        this.largePath = largePath;
        this.width = width;
        this.height = height;
        this.sizeBytes = sizeBytes;
        this.mimeType = mimeType;
        this.fileName = fileName;
    }

    public MultipartFile getOriginalFile() {
        return originalFile;
    }

    public void setOriginalFile(MultipartFile originalFile) {
        this.originalFile = originalFile;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getMediumPath() {
        return mediumPath;
    }

    public void setMediumPath(String mediumPath) {
        this.mediumPath = mediumPath;
    }

    public String getLargePath() {
        return largePath;
    }

    public void setLargePath(String largePath) {
        this.largePath = largePath;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public long getSizeBytes() {
        return sizeBytes;
    }

    public void setSizeBytes(long sizeBytes) {
        this.sizeBytes = sizeBytes;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
