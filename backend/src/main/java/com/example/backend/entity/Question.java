package com.example.backend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue
    private Long id;

    private String text;
    private String type;

    private int points;
    private int negativePoints;

    @ManyToOne
    private Exam exam;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Option> options;
}
