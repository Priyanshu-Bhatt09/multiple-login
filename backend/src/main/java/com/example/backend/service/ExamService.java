package com.example.backend.service;

import com.example.backend.dto.CreateExamDTO;
import com.example.backend.dto.ExamResponseDTO;
import com.example.backend.dto.OptionResponseDTO;
import com.example.backend.dto.QuestionResponseDTO;
import com.example.backend.entity.*;
import com.example.backend.repository.ExamRepository;
import com.example.backend.repository.LoginRepo;
import com.example.backend.repository.TestSettingsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ExamService {
    private final ExamRepository examRepository;
    private final LoginRepo userRepo;
    private final TestSettingsRepository testSettingsRepository;

    public ExamService(ExamRepository examRepository, LoginRepo userRepo, TestSettingsRepository testSettingsRepository) {
        this.examRepository = examRepository;
        this.userRepo = userRepo;
        this.testSettingsRepository = testSettingsRepository;
    }
    public Exam createExam(CreateExamDTO dto, User user) {
        Exam exam = new Exam();
        exam.setTitle(dto.getTitle());
        exam.setCreatedBy(user);
        exam.setTestLink(generateTestLink());

        return examRepository.save(exam);
    }
    public ExamResponseDTO getExamByLink(String link) {
        Exam exam = examRepository.findByTestLink(link)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        TestSettings settings = testSettingsRepository.findByExamId(exam.getId()).orElse(null);
        ExamResponseDTO response = new ExamResponseDTO();
        response.setExamId(exam.getId());
        response.setTitle(exam.getTitle());

        if (settings != null) {
            response.setDuration(settings.getDuration());
        }

            List<QuestionResponseDTO> questionDTOs = new ArrayList<>();

            for (Question q : exam.getQuestions()) {
                QuestionResponseDTO qDTO = new QuestionResponseDTO();
                qDTO.setQuestionId(q.getId());
                qDTO.setText(q.getText());
                qDTO.setType(q.getType());
                qDTO.setPoints(q.getPoints());
                qDTO.setNegativePoints(q.getNegativePoints());

                List<OptionResponseDTO> optionDtos = new ArrayList<>();

                for (Option opt : q.getOptions()) {
                    OptionResponseDTO oDto = new OptionResponseDTO();
                    oDto.setOptionId(opt.getId());
                    oDto.setText(opt.getText());
                    optionDtos.add(oDto);
                }
                qDTO.setOptions(optionDtos);
                questionDTOs.add(qDTO);
            }
            response.setQuestions(questionDTOs);
            return response;

    }

    private String generateTestLink() {
        return UUID.randomUUID().toString();
    }
}
