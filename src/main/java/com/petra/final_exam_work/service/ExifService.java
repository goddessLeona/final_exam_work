package com.petra.final_exam_work.service;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.petra.final_exam_work.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class ExifService {

    public void correctOrientation(
            String imagePath
    ) {

        File imageFile = new File(imagePath);

        int orientation = readOrientation(imageFile);
        System.out.println("Orientation: " + orientation);
        BufferedImage image = readImage(imageFile);

        BufferedImage rotated = rotate(image, orientation);

        try {
            ImageIO.write(
                    rotated,
                    "jpg",
                    imageFile
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int readOrientation(File file) {
        try {
            Metadata metadata =
                    ImageMetadataReader.readMetadata(file);

            ExifIFD0Directory directory =
                    metadata.getFirstDirectoryOfType(
                            ExifIFD0Directory.class
                    );

            if (directory == null || !directory.containsTag(ExifIFD0Directory.TAG_ORIENTATION)) {
                return 1;
            }

            return directory.getInt(
                    ExifIFD0Directory.TAG_ORIENTATION
            );


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private BufferedImage readImage(File file) {

        try {
            BufferedImage image = ImageIO.read(file);

            if (image == null) {
                throw new ApiException(
                        "Invalid image",
                        HttpStatus.BAD_REQUEST
                );
            }

            return image;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BufferedImage rotate(
            BufferedImage image,
            int orientation
    ) {
        switch (orientation) {
            case 3:
                return rotate180(image);

            case 6:
                return rotate90(image);

            case 8:
                return rotate270(image);

            default:
                return image;
        }
    }

    private BufferedImage rotate180(
            BufferedImage image
    ) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage rotated = new BufferedImage(
                width,
                height,
                BufferedImage.TYPE_INT_RGB
        );

        Graphics2D graphics = rotated.createGraphics();

        try {
            graphics.rotate(
                    Math.toRadians(180),
                    width / 2.0,
                    height / 2.0
            );

            graphics.drawImage(
                    image,
                    0,
                    0,
                    null
            );

        }finally {
            graphics.dispose();
        }

        return rotated;
    }

    private BufferedImage rotate90(
            BufferedImage image
    ) {
        int width = image.getWidth();
        int height = image.getHeight();

        int newHeight = width;
        int newWidth = height;

        BufferedImage rotated = new BufferedImage(
                newWidth,
                newHeight,
                BufferedImage.TYPE_INT_RGB
        );

        Graphics2D graphics = rotated.createGraphics();

        try {

            graphics.translate(width,0);
            graphics.rotate(Math.toRadians(90));

            graphics.drawImage(
                    image,
                    0,
                    0,
                    null
            );

        }finally {
            graphics.dispose();
        }

        return rotated;
    }


    private BufferedImage rotate270(
            BufferedImage image
    ) {

        int width = image.getWidth();
        int height = image.getHeight();

        int newHeight = width;
        int newWidth = height;

        BufferedImage rotated = new BufferedImage(
                newWidth,
                newHeight,
                BufferedImage.TYPE_INT_RGB
        );

        Graphics2D graphics = rotated.createGraphics();

        try {
            graphics.translate(0, height);
            graphics.rotate(Math.toRadians(270));

            graphics.drawImage(
                    image,
                    0,
                    0,
                    null
            );

        }finally {
            graphics.dispose();
        }

        return rotated;

    }

}
