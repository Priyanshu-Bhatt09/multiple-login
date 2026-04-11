package com.example.backend.dto;

import java.util.List;

public class ExamResponseDTO {
    private Long examId;
    private String title;
    private int duration;
    private List<QuestionResponseDTO> questions;

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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<QuestionResponseDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionResponseDTO> questions) {
        this.questions = questions;
    }

    private String testLink;
    private TestSettingsDto settings;

    public String getTestLink() {
        return testLink;
    }

    public void setTestLink(String testLink) {
        this.testLink = testLink;
    }

    public TestSettingsDto getSettings() {
        return settings;
    }

    public void setSettings(TestSettingsDto settings) {
        this.settings = settings;
    }
}
