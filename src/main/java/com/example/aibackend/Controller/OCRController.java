package com.example.aibackend.Controller;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class OCRController {
    @CrossOrigin(origins = "*")

    @PostMapping("/ocr")
    public ResponseEntity<String> performOCR(@RequestPart("image") MultipartFile image) {
        try {
            // Convert MultipartFile to BufferedImage
            BufferedImage bufferedImage = ImageIO.read(image.getInputStream());

            // Perform OCR
            String result = performOCR(bufferedImage);

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (IOException | TesseractException e) {
            e.printStackTrace();
            return new ResponseEntity<>("OCR Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String performOCR(BufferedImage image) throws TesseractException {
        System.setProperty("jna.library.path", "/usr/local/lib"); // Replace with the actual path to your Tesseract library

        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath("/Users/mac1/IdeaProjects/AIApiBackend/Tess4J/tessdata");
        tesseract.setTessVariable("user_defined_dpi", "1000");

        return tesseract.doOCR(image);
    }
}

