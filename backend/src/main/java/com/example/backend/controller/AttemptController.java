package com.example.backend.controller;

import com.example.backend.dto.SaveAnswerDto;
import com.example.backend.dto.StartAttemptDto;
import com.example.backend.dto.SubmitAttemptResponseDto;
import com.example.backend.entity.Attempt;
import com.example.backend.entity.User;
import com.example.backend.service.AttemptService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attempt")
public class AttemptController {
    private final AttemptService attemptService;

    public AttemptController(AttemptService attemptService) {
        this.attemptService = attemptService;
    }

    @PostMapping("/start")
    public org.springframework.http.ResponseEntity<?> startAttempt(@RequestBody StartAttemptDto dto,
                                @AuthenticationPrincipal User user) {
        try {
            Attempt attempt = attemptService.startAttempt(dto, user);
            return org.springframework.http.ResponseEntity.ok(java.util.Map.of("id", attempt.getId()));
        } catch (RuntimeException e) {
            return org.springframework.http.ResponseEntity.badRequest().body(java.util.Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/answer")
    public void saveAnswer(@RequestBody SaveAnswerDto dto) {
        attemptService.saveAnswer(dto);
    }

    @PostMapping("/submit/{attemptId}")
    public SubmitAttemptResponseDto submitAttempt(@PathVariable Long attemptId) {
        return attemptService.submitAttempt(attemptId);
    }

    @GetMapping("/my-attempts")
    public java.util.List<com.example.backend.dto.DashboardAttemptDto> getMyAttempts(@AuthenticationPrincipal User user) {
        return attemptService.getAttemptsByUser(user);
    }

    @PostMapping("/{attemptId}/violation")
    public void logViolation(@PathVariable Long attemptId, @RequestBody com.example.backend.dto.ViolationDto dto) {
        attemptService.logViolation(attemptId, dto);
    }

    @GetMapping("/{attemptId}/violations")
    public java.util.List<com.example.backend.entity.Violation> getViolations(@PathVariable Long attemptId) {
        return attemptService.getViolations(attemptId);
    }
}
