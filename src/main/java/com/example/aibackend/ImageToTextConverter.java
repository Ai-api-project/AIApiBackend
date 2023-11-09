package com.example.aibackend;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


@Configuration
public class ImageToTextConverter implements ApplicationRunner {

    public static void main(String [] args) {


        // Initialize Tesseract
        ITesseract tesseract = new Tesseract();

// Set Tesseract data path
        tesseract.setDatapath("C:\\Users\\Public\\Documents\\AIApiBackendCloned\\Tess4J\\tessdata");

// Specify the language (e.g., English)
// Set image DPI (dots per inch) to improve resolution
        tesseract.setTessVariable("user_defined_dpi", "1000"); // Adjust the DPI as needed

       // String folder = "C:\\Users\\Public\\Documents\\AIApiBackendCloned\\src\\main\\java\\com\\example\\aibackend\\";

// Specify the absolute image file path
        String imagePath = "C:\\Users\\Public\\Documents\\AIApiBackendCloned\\src\\main\\java\\com\\example\\aibackend\\georgebernardshaw1.jpg"; // Replace with the actual image file path

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

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
