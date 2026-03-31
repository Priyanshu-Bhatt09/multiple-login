package com.example.backend.entity;

import jakarta.persistence.*;

@Entity
public class Option {
    @Id
    @GeneratedValue
    private Long id;

    private String text;
    private boolean isCorrect;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;
}
