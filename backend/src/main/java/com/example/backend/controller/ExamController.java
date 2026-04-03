package com.example.backend.controller;

import com.example.backend.dto.CreateExamDTO;
import com.example.backend.dto.ExamResponseDTO;
import com.example.backend.entity.Exam;
import com.example.backend.entity.User;
import com.example.backend.service.ExamService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exam")
public class ExamController {
    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping
    public Exam createExam(@RequestBody CreateExamDTO dto, @AuthenticationPrincipal User user) {
        return examService.createExam(dto, user);
    }

    @GetMapping("/link/{link}")
    public ExamResponseDTO getExamByLink(@PathVariable String link) {
        return examService.getExamByLink(link);
    }
}
