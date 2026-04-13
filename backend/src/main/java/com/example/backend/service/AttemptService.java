package com.example.backend.service;

import com.example.backend.dto.SaveAnswerDto;
import com.example.backend.dto.StartAttemptDto;
import com.example.backend.dto.SubmitAttemptResponseDto;
import com.example.backend.entity.*;
import com.example.backend.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AttemptService {
    private final AttemptRepository attemptRepository;
    private final ExamRepository examRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;
    private final com.example.backend.repository.ViolationRepository violationRepository;
    private final com.example.backend.repository.TestSettingsRepository testSettingsRepository;

    public AttemptService(AttemptRepository attemptRepository,
                          ExamRepository examRepository,
                          AnswerRepository answerRepository,
                          QuestionRepository questionRepository,
                          OptionRepository optionRepository,
                          com.example.backend.repository.ViolationRepository violationRepository,
                          com.example.backend.repository.TestSettingsRepository testSettingsRepository) {
        this.attemptRepository = attemptRepository;
        this.examRepository = examRepository;
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.optionRepository = optionRepository;
        this.violationRepository = violationRepository;
        this.testSettingsRepository = testSettingsRepository;
    }

    public Attempt startAttempt(StartAttemptDto dto, User user) {
        Exam exam = examRepository.findById(dto.getExamId()).orElseThrow();

        // Enforce max attempts from settings
        com.example.backend.entity.TestSettings settings = testSettingsRepository.findByExamId(exam.getId()).orElse(null);
        if (settings != null && settings.getMaxAttempts() > 0) {
            long existingAttempts = attemptRepository.countByUser_IdAndExam_Id(user.getId(), exam.getId());
            if (existingAttempts >= settings.getMaxAttempts()) {
                throw new RuntimeException("Maximum attempts (" + settings.getMaxAttempts() + ") reached for this exam.");
            }
        }

        // Enforce start/end time window
        if (settings != null) {
            LocalDateTime now = LocalDateTime.now();
            if (settings.getStartTime() != null && now.isBefore(settings.getStartTime())) {
                throw new RuntimeException("This exam is not yet available. It opens at " + settings.getStartTime());
            }
            if (settings.getEndTime() != null && now.isAfter(settings.getEndTime())) {
                throw new RuntimeException("This exam is no longer available. It closed at " + settings.getEndTime());
            }
        }

        Attempt attempt = new Attempt();
        attempt.setExam(exam);
        attempt.setUser(user);
        attempt.setStartTime(LocalDateTime.now());

        return attemptRepository.save(attempt);
    }

    @org.springframework.transaction.annotation.Transactional
    public void saveAnswer(SaveAnswerDto dto) {
        Attempt attempt = attemptRepository.findById(dto.getAttemptId()).orElseThrow();
        Question question = questionRepository.findById(dto.getQuestionId()).orElseThrow();

        java.util.List<Answer> existingAnswers = answerRepository.findByAttempt_IdAndQuestion_Id(attempt.getId(), question.getId());
        Answer answer;
        if (!existingAnswers.isEmpty()) {
            answer = existingAnswers.get(0);
            if (existingAnswers.size() > 1) {
                for (int i = 1; i < existingAnswers.size(); i++) {
                    answerRepository.delete(existingAnswers.get(i));
                }
            }
        } else {
            answer = new Answer();
        }

        answer.setAttempt(attempt);
        answer.setQuestion(question);
        answer.setSelectedOptionId(dto.getSelectedOptionId());
        answer.setTextAnswer(dto.getTextAnswer());

        answerRepository.save(answer);
    }

    public SubmitAttemptResponseDto submitAttempt(Long attemptId) {
        Attempt attempt = attemptRepository.findById(attemptId).orElseThrow();

        java.util.List<Answer> answers = answerRepository.findByAttempt_Id(attemptId);
        int score = calculateScore(answers);
        attempt.setScore(score);
        attempt.setEndTime(LocalDateTime.now());

        Attempt saved =  attemptRepository.save(attempt);

        Integer returnScore = saved.getScore();
        com.example.backend.entity.TestSettings s = testSettingsRepository.findByExamId(saved.getExam().getId()).orElse(null);
        if (s != null && "NEVER".equalsIgnoreCase(s.getShowResult())) {
            returnScore = -1;
        }

        return new SubmitAttemptResponseDto(
                saved.getId(),
                returnScore,
                saved.getStartTime(),
                saved.getEndTime()
        );
    }

    private int calculateScore(java.util.List<Answer> answers) {
        int score = 0;
        for(Answer answer : answers) {
            Question q = answer.getQuestion();
            String qType = q.getType() != null ? q.getType() : "";

            if (qType.equals("MCA") || qType.equals("MULTIPLE_CHOICE_A")) {
                if (answer.getTextAnswer() != null && !answer.getTextAnswer().isEmpty() && !answer.getTextAnswer().equals("[]")) {
                    String clean = answer.getTextAnswer().replace("[", "").replace("]", "").replace(" ", "");
                    String[] ids = clean.split(",");
                    boolean allCorrect = true;
                    boolean anyWrong = false;
                    for (String strId : ids) {
                        if (strId.isEmpty()) continue;
                        try {
                            Long optId = Long.parseLong(strId);
                            Option opt = optionRepository.findById(optId).orElse(null);
                            if (opt != null) {
                                if (!opt.isCorrect()) {
                                    anyWrong = true;
                                }
                            }
                        } catch (Exception e) {}
                    }
                    
                    // Also check if they missed any correct options
                    long expectedCorrectCount = q.getOptions().stream().filter(Option::isCorrect).count();
                    long selectedCorrectCount = 0;
                    for (String strId : ids) {
                        if (strId.isEmpty()) continue;
                        try {
                            Long optId = Long.parseLong(strId);
                            Option opt = optionRepository.findById(optId).orElse(null);
                            if (opt != null && opt.isCorrect()) {
                                selectedCorrectCount++;
                            }
                        } catch (Exception e) {}
                    }

                    if (!anyWrong && selectedCorrectCount == expectedCorrectCount && expectedCorrectCount > 0) {
                        score += q.getPoints();
                    } else if (anyWrong || selectedCorrectCount > 0) { // They answered something but it's partially wrong or incomplete
                        score -= q.getNegativePoints();
                    }
                }
            } else if (answer.getSelectedOptionId() != null) {
                Option opt = optionRepository.findById(answer.getSelectedOptionId()).orElse(null);
                if (opt != null) {
                    if (opt.isCorrect()) {
                        score += q.getPoints();
                    } else {
                        score -= q.getNegativePoints();
                    }
                }
            } else if (qType.equals("CODING") || qType.equals("SINGLE_LINE_TEXT") || qType.equals("SHORT_TEXT")) {
                // simple exact text matching for short text or coding just for basic grading
                if (q.getCorrectCode() != null && !q.getCorrectCode().isEmpty()) {
                    if (q.getCorrectCode().trim().equals(answer.getTextAnswer() != null ? answer.getTextAnswer().trim() : "")) {
                        score += q.getPoints();
                    } else {
                        score -= q.getNegativePoints();
                    }
                }
            }
        }
        return score;
    }

    public java.util.List<com.example.backend.dto.DashboardAttemptDto> getAttemptsByUser(User user) {
        java.util.List<Attempt> attempts = attemptRepository.findByUser_Id(user.getId());
        java.util.List<com.example.backend.dto.DashboardAttemptDto> dtos = new java.util.ArrayList<>();
        for (Attempt a : attempts) {
            String title = a.getExam() != null ? a.getExam().getTitle() : "Unknown Exam";
            Long examId = a.getExam() != null ? a.getExam().getId() : null;
            Integer score = a.getScore();
            if (a.getExam() != null) {
                com.example.backend.entity.TestSettings s = testSettingsRepository.findByExamId(a.getExam().getId()).orElse(null);
                if (s != null && "NEVER".equalsIgnoreCase(s.getShowResult())) {
                    score = -1; // Indicate hidden score
                }
            }
            dtos.add(new com.example.backend.dto.DashboardAttemptDto(examId, title, score, a.getStartTime()));
        }
        return dtos;
    }

    public void logViolation(Long attemptId, com.example.backend.dto.ViolationDto dto) {
        Attempt attempt = attemptRepository.findById(attemptId).orElse(null);
        if (attempt != null) {
            com.example.backend.entity.Violation violation = new com.example.backend.entity.Violation();
            violation.setAttempt(attempt);
            violation.setType(dto.getType());
            violation.setMessage(dto.getMessage());
            violation.setImageUrl(dto.getImageUrl());
            violation.setTime(LocalDateTime.now());
            violationRepository.save(violation);
        }
    }

    public java.util.List<com.example.backend.entity.Violation> getViolations(Long attemptId) {
        return violationRepository.findByAttempt_Id(attemptId);
    }
}
