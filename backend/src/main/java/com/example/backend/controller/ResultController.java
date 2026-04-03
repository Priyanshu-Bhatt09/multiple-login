package com.example.backend.controller;


import com.example.backend.dto.ResultResponseDto;
import com.example.backend.entity.Attempt;
import com.example.backend.service.ResultService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/result")
public class ResultController {
    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping("/exam/{examId}")
    public List<ResultResponseDto> getResults(@PathVariable Long examId) {
        return  resultService.getResultsByExam(examId);
    }
}
