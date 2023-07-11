package com.example.quizapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "column_headers")
@Entity
@Data
public class ColumnHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "header_name")
    private String headerName;

    @Column(name = "header_value")
    private String headerValue;
}
