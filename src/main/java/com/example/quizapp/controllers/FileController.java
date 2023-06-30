package com.example.quizapp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequestMapping("api/files")
@RestController
@CrossOrigin(origins = "${frontend.url}")
@SuppressWarnings("unused")
public class FileController {

    private final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Value("${file.directory}")
    private String fileDirectory;

    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        String filePath = fileName; // Update with the actual file path

        Path path = Paths.get(filePath);
        Resource resource;
        try {
            logger.info("File Path: " + filePath);
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException("File not found", e);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment().filename(fileName).build());

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }


    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(MultipartFile file) {
        // Check if the file is not empty
        if (file.isEmpty()) {
            return new ResponseEntity<>("No file uploaded", HttpStatus.BAD_REQUEST);
        }

        try {
            // Save the file to the server
            String fileName = file.getOriginalFilename();
            System.out.println("File Name : " + fileName);
            
            if (fileName == null)
                return new ResponseEntity<>("No file uploaded", HttpStatus.BAD_REQUEST);
            File uploadFile = new File(fileDirectory, fileName);
            file.transferTo(uploadFile);

            // Perform further processing or business logic on the uploaded file

            return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to upload file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
