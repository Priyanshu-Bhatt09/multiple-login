package com.example.backend.service;

import com.example.backend.dto.CreateExamDTO;
import com.example.backend.dto.ExamResponseDTO;
import com.example.backend.dto.OptionResponseDTO;
import com.example.backend.dto.QuestionResponseDTO;
import com.example.backend.dto.DashboardExamDto;
import com.example.backend.entity.*;
import com.example.backend.repository.AttemptRepository;
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
    private final AttemptRepository attemptRepository;

    public ExamService(ExamRepository examRepository, LoginRepo userRepo, TestSettingsRepository testSettingsRepository, AttemptRepository attemptRepository) {
        this.examRepository = examRepository;
        this.userRepo = userRepo;
        this.testSettingsRepository = testSettingsRepository;
        this.attemptRepository = attemptRepository;
    }
    public Exam createExam(CreateExamDTO dto, User user) {
        Exam exam = new Exam();
        exam.setTitle(dto.getTitle());
        exam.setCreatedBy(user);
        exam.setTestLink(generateTestLink());

        return examRepository.save(exam);
    }
    public ExamResponseDTO getExamById(Long examId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found with id: " + examId));

        TestSettings settings = testSettingsRepository.findByExamId(exam.getId()).orElse(null);
        ExamResponseDTO response = new ExamResponseDTO();
        response.setExamId(exam.getId());
        response.setTitle(exam.getTitle());
        response.setTestLink(exam.getTestLink());

        if (settings != null) {
            response.setDuration(settings.getDuration());
            com.example.backend.dto.TestSettingsDto sDto = new com.example.backend.dto.TestSettingsDto();
            sDto.setDuration(settings.getDuration());
            sDto.setMaxAttempts(settings.getMaxAttempts());
            sDto.setEnableTimer(settings.isEnableTimer());
            sDto.setEnableProctor(settings.isEnableProctor());
            sDto.setTabSwitch(settings.isTabSwitch());
            sDto.setCamera(settings.isCamera());
            sDto.setMicrophone(settings.isMicrophone());
            sDto.setFullScreen(settings.isFullScreen());
            sDto.setMultiMonitor(settings.isMultiMonitor());
            sDto.setPhotosRandom(settings.isPhotosRandom());
            sDto.setShowResult(settings.getShowResult());
            if (settings.getStartTime() != null) {
                sDto.setStartTime(settings.getStartTime().toString());
            }
            if (settings.getEndTime() != null) {
                sDto.setEndTime(settings.getEndTime().toString());
            }
            response.setSettings(sDto);
        }

        List<QuestionResponseDTO> questionDTOs = new ArrayList<>();

        for (Question q : exam.getQuestions()) {
            QuestionResponseDTO qDTO = new QuestionResponseDTO();
            qDTO.setQuestionId(q.getId());
            qDTO.setText(q.getText());
            qDTO.setType(q.getType());
            qDTO.setPoints(q.getPoints());
            qDTO.setNegativePoints(q.getNegativePoints());
            qDTO.setParagraph(q.getParagraph());  // For CODING type
            qDTO.setCorrectCode(q.getCorrectCode());  // For CODING type

            List<OptionResponseDTO> optionDtos = new ArrayList<>();

            for (Option opt : q.getOptions()) {
                OptionResponseDTO oDto = new OptionResponseDTO();
                oDto.setOptionId(opt.getId());
                oDto.setText(opt.getText());
                oDto.setIsCorrect(opt.getIsCorrect());  // Include correct flag
                optionDtos.add(oDto);
            }
            qDTO.setOptions(optionDtos);
            questionDTOs.add(qDTO);
        }
        response.setQuestions(questionDTOs);
        return response;
    }

    public ExamResponseDTO getExamByLink(String link) {
        Exam exam = examRepository.findByTestLink(link)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        TestSettings settings = testSettingsRepository.findByExamId(exam.getId()).orElse(null);
        ExamResponseDTO response = new ExamResponseDTO();
        response.setExamId(exam.getId());
        response.setTitle(exam.getTitle());
        response.setTestLink(exam.getTestLink());

        if (settings != null) {
            response.setDuration(settings.getDuration());
            com.example.backend.dto.TestSettingsDto sDto = new com.example.backend.dto.TestSettingsDto();
            sDto.setDuration(settings.getDuration());
            sDto.setMaxAttempts(settings.getMaxAttempts());
            sDto.setEnableTimer(settings.isEnableTimer());
            sDto.setEnableProctor(settings.isEnableProctor());
            sDto.setTabSwitch(settings.isTabSwitch());
            sDto.setCamera(settings.isCamera());
            sDto.setMicrophone(settings.isMicrophone());
            sDto.setFullScreen(settings.isFullScreen());
            sDto.setMultiMonitor(settings.isMultiMonitor());
            sDto.setPhotosRandom(settings.isPhotosRandom());
            sDto.setShowResult(settings.getShowResult());
            if (settings.getStartTime() != null) {
                sDto.setStartTime(settings.getStartTime().toString());
            }
            if (settings.getEndTime() != null) {
                sDto.setEndTime(settings.getEndTime().toString());
            }
            response.setSettings(sDto);
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

    public List<DashboardExamDto> getExamsByUser(User user) {
        List<Exam> exams = examRepository.findByCreatedBy_Id(user.getId());
        List<DashboardExamDto> dtos = new ArrayList<>();
        for (Exam e : exams) {
            int participants = attemptRepository.findByExamId(e.getId()).size();
            dtos.add(new DashboardExamDto(e.getId(), e.getTitle(), participants));
        }
        return dtos;
    }
}
