package com.example.dormnestapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Table(name = "room")
@Entity
@Data
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "room_no")
    private long roomNumber;

    @Column(name = "limit_value")
    private long limit;

    @ManyToMany
    @JsonIgnore
    private List<Person> persons;

}
