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

    @Column(columnDefinition = "varchar(255) default 'IMMEDIATELY'")
    private String showResult = "IMMEDIATELY";

    @OneToOne
    private Exam exam;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public boolean isEnableTimer() {
        return enableTimer;
    }

    public void setEnableTimer(boolean enableTimer) {
        this.enableTimer = enableTimer;
    }

    public boolean isEnableProctor() {
        return enableProctor;
    }

    public void setEnableProctor(boolean enableProctor) {
        this.enableProctor = enableProctor;
    }

    public boolean isTabSwitch() {
        return tabSwitch;
    }

    public void setTabSwitch(boolean tabSwitch) {
        this.tabSwitch = tabSwitch;
    }

    public boolean isCamera() {
        return camera;
    }

    public void setCamera(boolean camera) {
        this.camera = camera;
    }

    public boolean isMicrophone() {
        return microphone;
    }

    public void setMicrophone(boolean microphone) {
        this.microphone = microphone;
    }

    public boolean isFullScreen() {
        return fullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
    }

    public boolean isMultiMonitor() {
        return multiMonitor;
    }

    public void setMultiMonitor(boolean multiMonitor) {
        this.multiMonitor = multiMonitor;
    }

    public boolean isPhotosRandom() {
        return photosRandom;
    }

    public void setPhotosRandom(boolean photosRandom) {
        this.photosRandom = photosRandom;
    }

    public String getShowResult() {
        return showResult != null ? showResult : "IMMEDIATELY";
    }

    public void setShowResult(String showResult) {
        this.showResult = showResult;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }
}
