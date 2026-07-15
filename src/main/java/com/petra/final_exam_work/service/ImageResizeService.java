package com.petra.final_exam_work.service;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class ImageResizeService {


    public BufferedImage resize(
            BufferedImage original,
            int maxWidth
    ) {

        int originalWidth = original.getWidth();
        int originalHeight = original.getHeight();

        // Do not enlarge small images
        if (originalWidth <= maxWidth) {
            return original;
        }

        // resize logic
        double scale = (double) maxWidth / originalWidth;
        int newWidth = (int) (originalWidth * scale);
        int newHeight= (int) (originalHeight * scale);

        BufferedImage resizedImage = new BufferedImage(
                newWidth,
                newHeight,
                BufferedImage.TYPE_INT_RGB
        );

        // draw original image
        Graphics2D graphics = resizedImage.createGraphics();

        graphics.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR
        );

        try {
            graphics.drawImage(
                    original,
                    0,
                    0,
                    newWidth,
                    newHeight,
                    null
            );
        } finally {
            graphics.dispose();
        }

        return resizedImage;
    }

    public String resizeAndSave(
           String imagePath,
           int maxWidth,
           String suffix
    ) {

        File originalFile = new File(imagePath);
        String fileName = originalFile.getName();

        int dot = fileName.lastIndexOf(".");
        String name = fileName.substring(0, dot);
        String extension = fileName.substring(dot);

        String newFileName = name + suffix + extension;

        try {
            BufferedImage original = ImageIO.read(new File(imagePath));
            BufferedImage resized = resize(original, maxWidth);

            System.out.println(
                    "Resized: " + resized.getWidth() + " x " + resized.getHeight()
            );

            File outputFile = new File(
                    originalFile.getParent(),
                    newFileName
            );

            ImageIO.write(
                    resized,
                    "jpg",
                    outputFile);

            return outputFile.getPath();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
