package com.example.dormnestapp.services;

import com.example.dormnestapp.dao.FeeDao;
import com.example.dormnestapp.dao.PersonDao;
import com.example.dormnestapp.model.Fee;
import com.example.dormnestapp.model.FeeBO;
import com.example.dormnestapp.model.Person;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@SuppressWarnings("unused")
public class FeeService {

    @Autowired
    private final FeeDao feeDao;

    @Autowired
    private final PersonDao personDao;

    public FeeService(FeeDao feeDao, PersonDao personDao) {
        this.feeDao = feeDao;
        this.personDao = personDao;
    }


    public ResponseEntity<String> createFeeEntry(Fee fee) {
        try { // Retrieve the associated person from the database
            Person person = personDao.findById(fee.getPerson().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid person id"));

            fee.setPerson(person);

            // Save the fee object to the database
            feeDao.save(fee);

            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed to create fee entry", HttpStatus.BAD_REQUEST);
    }


    //    TODO:
    public ResponseEntity<List<Fee>> getPaymentsByPaidDate(LocalDate paidDate) {
        try {

            return new ResponseEntity<>(feeDao.findByDate(paidDate), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<FeeBO>> getPaymentsByAllIds(String id) {
        try {

            List<Fee> paymentsByRefId = feeDao.findByRefId(id);

            List<Fee> paymentsByPersonId = feeDao.findByPersonId(Long.parseLong(id));
            List<Fee> allPayments = Stream.concat(paymentsByRefId.stream(), paymentsByPersonId.stream()).toList();

            List<FeeBO> dtoList = allPayments.stream().map(this::convertFeeToFeeBO).toList();

            return ResponseEntity.ok(dtoList);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return ResponseEntity.ok(new ArrayList<>());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.badRequest().body(new ArrayList<>());
    }

    private FeeBO convertFeeToFeeBO(Fee fee) {
        FeeBO feeBO = new FeeBO();
        feeBO.setId(fee.getId());
        feeBO.setAmount(fee.getAmount());
        feeBO.setCurrency(fee.getCurrency());

        Optional<Person> person = Optional.ofNullable(fee.getPerson()).map(this::getPersonBO);

        person.ifPresent(feeBO::setPerson);

        feeBO.setRefId(fee.getRefId());
        feeBO.setPaidDate(fee.getPaidDate());
        feeBO.setPaymentType(fee.getPaymentType());

        return feeBO;
    }

    private Person getPersonBO(Person person) {
        Person personBO = new Person();
        personBO.setId(person.getId());
        personBO.setFirstname(person.getFirstname());
        personBO.setLastname(person.getLastname());
        personBO.setMobile(person.getMobile());
        personBO.setEmail(person.getEmail());
        personBO.setAddress(person.getAddress());
        personBO.setCompany(person.getCompany());
        personBO.setIsActive(person.getIsActive());
        return personBO;
    }


    public ResponseEntity<List<Fee>> getAllPaymentDetails() {
        try {
            return new ResponseEntity<>(feeDao.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Resource> generateFeeReceipts() {
        List<Person> persons = personDao.findAll();

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             PdfWriter writer = new PdfWriter(baos);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            // Set default font
            document.setFont(PdfFontFactory.createFont());

            // Add heading
            Paragraph heading = new Paragraph("Fee Receipt")
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
