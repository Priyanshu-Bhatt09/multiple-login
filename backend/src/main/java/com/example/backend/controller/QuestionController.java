package com.example.backend.controller;

import com.example.backend.dto.AddQuestionDTO;
import com.example.backend.entity.Question;
import com.example.backend.service.QuestionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public Question addQuestion(@RequestBody AddQuestionDTO dto) {
        return questionService.addQuestion(dto);
    }

    @GetMapping("/exam/{examId}")
    public List<Question> getQuestions(@PathVariable Long examId) {
        return questionService.getQuestionsByExam(examId);
    }
}
