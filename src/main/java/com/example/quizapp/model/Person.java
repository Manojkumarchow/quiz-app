package com.example.quizapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "persons")
@Entity
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @Column(name = "mobile")
    private String mobile;

    private String email;

    private String address;

    private String company;

    @Column(name = "is_active")
    private String isActive;

}
