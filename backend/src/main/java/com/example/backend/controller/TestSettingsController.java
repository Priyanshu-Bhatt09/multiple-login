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

    //publish result
    @PostMapping("/{examId}/publish-results")
    public java.util.Map<String, String> publishResults(@PathVariable Long examId) {
        TestSettings settings = testSettingsService.getSettings(examId);
        settings.setShowResult("IMMEDIATELY");
        com.example.backend.dto.TestSettingsDto dto = new com.example.backend.dto.TestSettingsDto();
        dto.setExamId(examId);
        dto.setDuration(settings.getDuration());
        dto.setMaxAttempts(settings.getMaxAttempts());
        dto.setEnableTimer(settings.isEnableTimer());
        dto.setEnableProctor(settings.isEnableProctor());
        dto.setTabSwitch(settings.isTabSwitch());
        dto.setCamera(settings.isCamera());
        dto.setMicrophone(settings.isMicrophone());
        dto.setFullScreen(settings.isFullScreen());
        dto.setMultiMonitor(settings.isMultiMonitor());
        dto.setPhotosRandom(settings.isPhotosRandom());
        dto.setShowResult("IMMEDIATELY");
        if (settings.getStartTime() != null) dto.setStartTime(settings.getStartTime().toString());
        if (settings.getEndTime() != null) dto.setEndTime(settings.getEndTime().toString());
        testSettingsService.saveSettings(dto);
        return java.util.Map.of("message", "Results published successfully");
    }
}
