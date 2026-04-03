package com.example.backend.dto;

import java.time.LocalDateTime;

public class ResultResponseDto {
    private Long attemptId;
    private String studentName;
    private int score;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public ResultResponseDto(Long attemptId, String studentName, int score, LocalDateTime startTime, LocalDateTime endTime) {
        this.attemptId = attemptId;
        this.score = score;
        this.studentName = studentName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(Long attemptId) {
        this.attemptId = attemptId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
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
