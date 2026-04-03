package com.example.backend.dto;

import java.time.LocalDateTime;

public class SubmitAttemptResponseDto {
    private Long attemptId;
    private int score;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public SubmitAttemptResponseDto(Long attemptId, int score, LocalDateTime startTime, LocalDateTime endTime) {
        this.attemptId = attemptId;
        this.score = score;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(Long attemptId) {
        this.attemptId = attemptId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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
}
