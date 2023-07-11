package com.example.dormnestapp.controllers;

import com.example.dormnestapp.dao.PersonDao;
import com.example.dormnestapp.model.Fee;
import com.example.dormnestapp.model.FeeBO;
import com.example.dormnestapp.services.FeeService;
import com.example.dormnestapp.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/fee")
@SuppressWarnings("unused")
public class FeeController {

    @Autowired
    private final PersonService personService;

    @Autowired
    private final PersonDao personDao;

    @Autowired
    private final FeeService feeService;

    public FeeController(PersonService personService, PersonDao personDao, FeeService feeService) {
        this.personService = personService;
        this.personDao = personDao;
        this.feeService = feeService;
    }

    @GetMapping("/allPayments")
    public ResponseEntity<List<Fee>> getAllPaymentDetails() {
        return feeService.getAllPaymentDetails();
    }

    @GetMapping("/:id")
    public ResponseEntity<List<FeeBO>> getPaymentsByPerson(@PathVariable String id) {
        return feeService.getPaymentsByAllIds(id);
    }

    @PostMapping("/createPayment")
    public ResponseEntity<String> createFee(@RequestBody Fee fee) {
        return feeService.createFeeEntry(fee);
    }

    @GetMapping("/pdf/persons")
    public ResponseEntity<Resource> generatePersonPDF() {
        return feeService.generateFeeReceipts();
    }
}
