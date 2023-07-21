package com.example.dormnestapp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Table(name = "persons")
@Entity
@Data
public class Person implements Serializable {

    @Serial
    private static final long serialVersionUID = 7156526077883281623L;

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

    @Column(name = "emergency_contact")
    private String emergencyContact;

}
