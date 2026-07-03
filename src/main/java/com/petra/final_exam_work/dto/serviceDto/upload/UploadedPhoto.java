package com.petra.final_exam_work.dto.serviceDto.upload;

import org.springframework.web.multipart.MultipartFile;

public class UploadedPhoto {

    private MultipartFile originalFile;

    private String thumbnailPath;
    private String mediumPath;
    private String largePath;
    private long sizeBytes;
    private String mimeType;
}
