package com.example.backend.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class Exam {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public String getTestLink() {
        return testLink;
    }

    public void setTestLink(String testLink) {
        this.testLink = testLink;
    }

    @Column(unique = true, nullable = false, updatable = false)
    private String testLink;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private User createdBy;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;

    @OneToOne(mappedBy = "exam", cascade = CascadeType.ALL)
    private TestSettings settings;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public TestSettings getSettings() {
        return settings;
    }

    public void setSettings(TestSettings settings) {
        this.settings = settings;
    }
}
