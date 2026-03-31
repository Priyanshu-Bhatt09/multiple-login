package com.example.backend.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Exam {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(unique = true, nullable = false, updatable = false)
    private String testLink = UUID.randomUUID().toString();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private User createdBy;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;

    @OneToOne(mappedBy = "exam", cascade = CascadeType.ALL)
    private TestSettings settings;
}
