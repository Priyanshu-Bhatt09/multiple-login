package com.example.backend.dto;

public class OptionDto {
    private String text;
    @com.fasterxml.jackson.annotation.JsonProperty("isCorrect")
    private boolean isCorrect;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
