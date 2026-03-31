package com.example.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Attempt {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private int score;

    @ManyToOne
    private User user;

    @ManyToOne
    private Exam exam;

    @OneToMany(mappedBy = "attempt", cascade = CascadeType.ALL)
    private List<Answer> answers;
}
