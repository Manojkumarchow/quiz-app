package com.example.dormnestapp.services;

import com.example.dormnestapp.dao.ColumnHeaderDao;
import com.example.dormnestapp.dao.QuestionDao;
import com.example.dormnestapp.model.ColumnHeader;
import com.example.dormnestapp.model.Question;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {

    private final Logger logger = LoggerFactory.getLogger(FileService.class);
    @Autowired
    private final QuestionDao questionDao;

    @Autowired
    private final ColumnHeaderDao columnHeaderDao;

    @Value("${file.directory}")
    private String fileDirectory;

    public FileService(QuestionDao questionDao, ColumnHeaderDao columnHeaderDao) {
        this.questionDao = questionDao;
        this.columnHeaderDao = columnHeaderDao;
    }

    public ResponseEntity<Resource> downloadExcel() {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sample Data");

            // Creating a custom font
            Font font = workbook.createFont();
            font.setFontName("Arial");
            font.setFontHeightInPoints((short) 12);
            font.setBold(true);

            // Creating a custom header style
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFont(font);

            // Creating the header row
            Row headerRow = sheet.createRow(0);
            List<String> headerValues = getHeaderValues();

            for (int colNum = 0; colNum < headerValues.size(); colNum++) {
                Cell cell = headerRow.createCell(colNum);
                cell.setCellValue(headerValues.get(colNum));
                cell.setCellStyle(headerStyle);
            }

            // Creating sample data
            List<Question> questions = questionDao.findAll();
            int questionNumber = 0;
            for (int rowNum = 1; rowNum <= questions.size(); rowNum++) {
                Row row = sheet.createRow(rowNum);
                int colNum = 0;

                questionNumber = insertCellData(questions, questionNumber, row, colNum);
            }

            // Autosizing the columns
            for (int colNum = 0; colNum < headerValues.size(); colNum++) {
                sheet.autoSizeColumn(colNum);
            }

            // Writing workbook data to a ByteArrayOutputStream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            // Create a ByteArrayResource from the ByteArrayOutputStream
            ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

            // Set the response headers for file download
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=sample_data.xlsx");

            // Return the ResponseEntity with ByteArrayResource, headers, and HTTP status
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(resource.contentLength())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private int insertCellData(List<Question> questions, int questionNumber, Row row, int colNum) {
        Cell cell = row.createCell(colNum);
        cell.setCellValue(questions.get(questionNumber).getCategory());
        colNum++;

        cell = row.createCell(colNum);
        cell.setCellValue(questions.get(questionNumber).getDifficulty());
        colNum++;

        cell = row.createCell(colNum);
        cell.setCellValue(questions.get(questionNumber).getQuestionTitle());
        colNum++;

        cell = row.createCell(colNum);
        cell.setCellValue(questions.get(questionNumber).getOption1());
        colNum++;

        cell = row.createCell(colNum);
        cell.setCellValue(questions.get(questionNumber).getOption2());
        colNum++;

        cell = row.createCell(colNum);
        cell.setCellValue(questions.get(questionNumber).getOption3());
        colNum++;

        cell = row.createCell(colNum);
        cell.setCellValue(questions.get(questionNumber).getRightAnswer());

        questionNumber++;
        return questionNumber;
    }

    private List<String> getHeaderValues() {
        List<ColumnHeader> columns = columnHeaderDao.findAll();
        return columns.stream()
                .map(ColumnHeader::getHeaderValue)
                .collect(Collectors.toList());
    }


    public ResponseEntity<String> uploadFile(MultipartFile file) {
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

    public ResponseEntity<Resource> downloadAnyFile(String fileName) {
        Path path = Paths.get(fileName);
        Resource resource;
        try {
            logger.info("File Path: " + fileName);
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
}
