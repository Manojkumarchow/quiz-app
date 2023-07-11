package com.example.quizapp.controllers;

import com.example.quizapp.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("api/files")
@RestController
@CrossOrigin(origins = "${frontend.url}")
@SuppressWarnings("unused")
public class FileController {

    @Autowired
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        return fileService.downloadAnyFile(fileName);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(MultipartFile file) {
        return fileService.uploadFile(file);
    }

    @GetMapping(value = "/download-excel", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> downloadExcel() {
        return fileService.downloadExcel();
    }

}
