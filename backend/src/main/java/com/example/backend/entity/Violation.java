package com.example.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

@Entity
public class Violation {
    @Id
    @GeneratedValue
    private Long id;

    private String type;
    private LocalDateTime time;

    @ManyToOne
    private Attempt attempt;
}
