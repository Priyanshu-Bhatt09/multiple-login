package com.example.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class TestSettings {
    @Id
    @GeneratedValue
    private Long id;

    private int duration;
    private int maxAttempts;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private boolean enableTimer;
    private boolean enableProctor;

    private boolean tabSwitch;
    private boolean camera;
    private boolean microphone;
    private boolean fullScreen;
    private boolean multiMonitor;
    private boolean photosRandom;

    @OneToOne
    private Exam exam;
}
