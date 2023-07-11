package com.example.dormnestapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "questions")
@Entity
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String category;
    private String difficulty;
    @Column(name = "option_1")
    private String option1;
    @Column(name = "option_2")
    private String option2;
    @Column(name = "option_3")
    private String option3;
    @Column(name = "question_title")
    private String questionTitle;
    @Column(name = "right_answer")
    private String rightAnswer;
}

