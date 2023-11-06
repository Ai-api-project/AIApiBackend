package com.example.aibackend;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageToTextConverter {

    public static void main(String[] args) {
        // Initialize Tesseract
        ITesseract tesseract = new Tesseract();
        // tesseract.setDatapath("/path/to/tessdata"); // Set the path to your tessdata directory

        // Specify the absolute image file path
        String imagePath = "/Users/mac1/Desktop/download.png"; // Replace with the actual absolute image file path

        try {
            // Load and display the image
            File imageFile = new File(imagePath);
            BufferedImage image = ImageIO.read(imageFile);

            if (image != null) {
                // Display the image in a JFrame
                JFrame frame = new JFrame("Image Viewer");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(new JLabel(new ImageIcon(image)));
                frame.pack();
                frame.setVisible(true);

                // Perform OCR
                String result = tesseract.doOCR(imageFile);
                System.out.println("OCR Result:");
                System.out.println(result);
            } else {
                System.err.println("Failed to load the image.");
            }
        } catch (IOException | TesseractException e) {
            e.printStackTrace();
            System.err.println("OCR Error: " + e.getMessage());
        }
    }
}
