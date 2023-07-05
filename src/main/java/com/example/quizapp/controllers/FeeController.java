package com.example.quizapp.controllers;

import com.example.quizapp.model.Person;
import com.example.quizapp.services.PersonService;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/fee")
@SuppressWarnings("unused")
public class FeeController {

    @Autowired
    private final PersonService personService;

    public FeeController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/pdf/persons")
    public ResponseEntity<Resource> generatePersonPDF() {
        List<Person> persons = personService.getAllPersons();

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             PdfWriter writer = new PdfWriter(baos);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            // Set default font
            document.setFont(PdfFontFactory.createFont());

            // Add heading
            Paragraph heading = new Paragraph("My PERSONS")
                    .setFontSize(20)
                    .setBold()
                    .setMarginBottom(20)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(heading);

            // Add address
            Paragraph address = new Paragraph("Dummy Address")
                    .setFontSize(12)
                    .setMarginBottom(40)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(address);

            // Create a table
            Table table = new Table(new float[]{1, 1, 1, 1, 1, 1, 1})
                    .setMarginBottom(20);

            // Add table headers
            table.addHeaderCell("ID");
            table.addHeaderCell("First Name");
            table.addHeaderCell("Last Name");
            table.addHeaderCell("Mobile");
            table.addHeaderCell("Email");
            table.addHeaderCell("Address");
            table.addHeaderCell("Company");

            // Add table rows
            for (Person person : persons) {
                table.addCell(String.valueOf(person.getId()));
                table.addCell(person.getFirstname());
                table.addCell(person.getLastname());
                table.addCell(person.getMobile());
                table.addCell(person.getEmail());
                table.addCell(person.getAddress());
                table.addCell(person.getCompany());
            }

            // Set maximum height for the table
            float tableHeight = document.getPdfDocument().getDefaultPageSize().getHeight() - document.getTopMargin() - document.getBottomMargin();
            table.setMaxHeight(tableHeight);

            // Enable overflow handling for the table
            table.setKeepTogether(true);

            // Auto-size the table to fit the page width
            table.setAutoLayout();

            // Add the table to the document
            document.add(table);

            // Add payment details
            LocalDate currentDate = LocalDate.now();
            String currentMonth = currentDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            String paymentDetails = "Name: {personName} has paid an amount of {amount} INR for the month of " + currentMonth;
            paymentDetails = paymentDetails.replace("{personName}", "John Doe"); // Replace with actual person's name
            paymentDetails = paymentDetails.replace("{amount}", "1000"); // Replace with actual payment amount

            Paragraph payment = new Paragraph(paymentDetails)
                    .setFontSize(12)
                    .setMarginTop(40)
                    .setMarginBottom(20);
            document.add(payment);

            // Add date and signature
            String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            Paragraph date = new Paragraph("Date: " + formattedDate)
                    .setFontSize(12)
                    .setMarginBottom(20)
                    .setTextAlignment(TextAlignment.LEFT);

            Paragraph signature = new Paragraph("Signature:")
                    .setFontSize(12)
                    .setFixedPosition(pdf.getDefaultPageSize().getWidth() - 100, 50, 100)
                    .setVerticalAlignment(VerticalAlignment.TOP)
                    .setMarginTop(40)
                    .setTextAlignment(TextAlignment.RIGHT);

            document.add(date);
            document.add(signature);

            // Close the document
            document.close();

            // Save the PDF to a temporary file
            String tempFileName = "persons.pdf";
            File tempFile = File.createTempFile("temp_", "_" + tempFileName);
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(baos.toByteArray());
            }

            // Create a Resource object from the temporary file
            Path tempFilePath = tempFile.toPath();
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(tempFilePath));

            // Set the response headers for file download
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + tempFileName);

            // Delete the temporary file after the download is complete
            tempFile.deleteOnExit();

            // Return the response entity with the file content and headers
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(tempFile.length())
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
