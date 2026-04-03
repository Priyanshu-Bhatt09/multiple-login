package com.example.backend.dto;

import java.util.List;

public class QuestionResponseDTO {
    private Long questionId;
    private String text;
    private String type;
    private int points;
    private int negativePoints;
    private List<OptionResponseDTO> options;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getNegativePoints() {
        return negativePoints;
    }

    public void setNegativePoints(int negativePoints) {
        this.negativePoints = negativePoints;
    }

    public List<OptionResponseDTO> getOptions() {
        return options;
    }
    public void setOptions(List<OptionResponseDTO> options) {
        this.options = options;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
}



