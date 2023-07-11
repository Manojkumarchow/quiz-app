package com.example.quizapp.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FeeBO {
    private long id;
    private long amount;
    private String currency;
    private String refId;
    private LocalDate paidDate;
    private String paymentType;

    private Person person;
    // Other fields as needed
}
