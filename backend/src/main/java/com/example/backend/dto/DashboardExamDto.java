package com.example.backend.dto;

public class DashboardExamDto {
    private Long id;
    private String title;
    private int participants;

    public DashboardExamDto(Long id, String title, int participants) {
        this.id = id;
        this.title = title;
        this.participants = participants;
    }

    public DashboardExamDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }
}
