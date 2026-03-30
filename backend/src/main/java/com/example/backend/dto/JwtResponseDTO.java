package com.example.backend.dto;

public class JwtResponseDTO {
    private String token;
    private String message;
    public JwtResponseDTO(String token, String message) {this.token = token;
    this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
