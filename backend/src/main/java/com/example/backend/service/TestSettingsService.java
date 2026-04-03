package com.example.backend.service;

import com.example.backend.dto.TestSettingsDto;
import com.example.backend.entity.Exam;
import com.example.backend.entity.TestSettings;
import com.example.backend.repository.ExamRepository;
import com.example.backend.repository.TestSettingsRepository;
import org.springframework.stereotype.Service;

@Service
public class TestSettingsService {
    private final TestSettingsRepository settingsRepository;
    private final ExamRepository examRepository;

    public TestSettingsService(TestSettingsRepository settingsRepository,
                               ExamRepository examRepository) {
        this.settingsRepository = settingsRepository;
        this.examRepository = examRepository;
    }

    public TestSettings saveSettings(TestSettingsDto dto) {
        Exam exam = examRepository.findById(dto.getExamId()).orElseThrow();

        TestSettings settings = new TestSettings();
        settings.setExam(exam);
        settings.setDuration(dto.getDuration());
        settings.setMaxAttempts(dto.getMaxAttempts());
        settings.setEnableProctor(dto.isEnableProctor());
        settings.setTabSwitch(dto.isTabSwitch());
        settings.setCamera(dto.isCamera());
        settings.setMicrophone(dto.isMicrophone());
        settings.setFullScreen(dto.isFullScreen());
        settings.setMultiMonitor(dto.isMultiMonitor());

        return settingsRepository.save(settings);
    }

    public TestSettings  getSettings(Long examId) {
        return settingsRepository.findByExamId(examId).orElseThrow();
    }
}
