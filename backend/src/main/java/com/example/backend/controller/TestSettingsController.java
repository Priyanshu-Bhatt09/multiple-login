package com.example.backend.controller;

import com.example.backend.dto.TestSettingsDto;
import com.example.backend.entity.TestSettings;
import com.example.backend.service.TestSettingsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/settings")
public class TestSettingsController {
    private final TestSettingsService testSettingsService;

    public TestSettingsController(TestSettingsService testSettingsService) {
        this.testSettingsService = testSettingsService;
    }

    //save settings
    @PostMapping
    public TestSettings saveSettings(@RequestBody TestSettingsDto dto) {
        return testSettingsService.saveSettings(dto);
    }


    //get setting by exam
    @GetMapping("/{examId}")
    public TestSettings getSettings(@PathVariable Long examId) {
        return testSettingsService.getSettings(examId);
    }
}
