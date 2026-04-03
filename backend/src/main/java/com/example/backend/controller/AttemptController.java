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
    public Attempt startAttempt(@RequestBody StartAttemptDto dto,
                                @AuthenticationPrincipal User user) {
        return attemptService.startAttempt(dto, user);
    }

    @PostMapping("/answer")
    public void saveAnswer(@RequestBody SaveAnswerDto dto) {
        attemptService.saveAnswer(dto);
    }

    @PostMapping("/submit/{attemptId}")
    public SubmitAttemptResponseDto submitAttempt(@PathVariable Long attemptId) {
        return attemptService.submitAttempt(attemptId);
    }
}
