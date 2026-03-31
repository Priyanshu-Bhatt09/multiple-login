package com.example.backend.entity;

import jakarta.persistence.*;

@Entity
public class Answer {
    @Id
    @GeneratedValue
    private Long id;

    private String textAnswer;
    private Long selectedOptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attempt_id")
    private Attempt attempt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;
}
