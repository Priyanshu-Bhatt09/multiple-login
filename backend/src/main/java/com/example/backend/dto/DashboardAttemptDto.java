package com.example.backend.dto;

import java.time.LocalDateTime;

public class DashboardAttemptDto {
    private Long examId;
    private String title;
    private int score;
    private LocalDateTime dateTaken;

    public DashboardAttemptDto(Long examId, String title, int score, LocalDateTime dateTaken) {
        this.examId = examId;
        this.title = title;
        this.score = score;
        this.dateTaken = dateTaken;
    }

    public DashboardAttemptDto() {}

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public LocalDateTime getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(LocalDateTime dateTaken) {
        this.dateTaken = dateTaken;
    }
}
