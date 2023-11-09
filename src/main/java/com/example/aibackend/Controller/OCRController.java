package com.example.aibackend.Controller;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
@CrossOrigin(origins = "*")
public class OCRController {

    @CrossOrigin(origins = "*", allowedHeaders = "*")
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


    @GetMapping("/")
    @ResponseBody
    public String hello() {
        return "hej";
    }

    private String performOCR(BufferedImage image) throws TesseractException {
        ITesseract tesseract = new Tesseract();

        // Get the tessdata path from the environment variable
        String tessdataPath = System.getenv("TESSDATA_PATH");

        System.out.println("TESSDATA_PATH: " + tessdataPath);  // Log the tessdata path

        if (tessdataPath != null && !tessdataPath.isEmpty()) {
            tesseract.setDatapath(tessdataPath);
            tesseract.setTessVariable("user_defined_dpi", "1000");

            try {
                String result = tesseract.doOCR(image);
                System.out.println("OCR Result: " + result);  // Log the OCR result
                return result;
            } catch (TesseractException e) {
                System.out.println("OCR Error: " + e.getMessage());  // Log the OCR error
                throw e;  // Re-throw the exception for further handling
            }
        } else {
            throw new TesseractException("TESSDATA_PATH environment variable not set.");
        }
    }






}

