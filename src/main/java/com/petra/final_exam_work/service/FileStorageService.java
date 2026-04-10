package com.petra.final_exam_work.service;

import com.petra.final_exam_work.exception.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String baseUploadDir;

    public String save(
            MultipartFile file,
            UUID userUuid,
            String category,
            String filePrefix
    ){

        try{

            Path userDir = Paths.get(
                    baseUploadDir,
                "users",
                userUuid.toString(),
                category
            );

            if (!Files.exists(userDir)) {
                Files.createDirectories(userDir);
            }

            // Generate safe unique filename
            String extension = getFileExtension(file.getOriginalFilename());
            String uniqueName = filePrefix + "_" + UUID.randomUUID() + extension;

            Path filePath = userDir.resolve(uniqueName);

            Files.copy(
                    file.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return baseUploadDir + "/users/"
                    + userUuid + "/"
                    + category + "/"
                    + uniqueName;

        }catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf("."));
    }

    public void validateImage(MultipartFile file) {

        try (InputStream is = file.getInputStream()) {

            BufferedImage image = ImageIO.read(is);

            if (image == null) {
                throw new ApiException("Invalid image file",
                        HttpStatus.BAD_REQUEST);
            }

        } catch (IOException e) {
            throw new ApiException("Invalid image file",
                    HttpStatus.BAD_REQUEST);
        }
    }


}
