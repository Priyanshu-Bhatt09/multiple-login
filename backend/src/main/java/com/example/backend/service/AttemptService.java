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

    public AttemptService(AttemptRepository attemptRepository,
                          ExamRepository examRepository,
                          AnswerRepository answerRepository,
                          QuestionRepository questionRepository,
                          OptionRepository optionRepository) {
        this.attemptRepository = attemptRepository;
        this.examRepository = examRepository;
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.optionRepository = optionRepository;
    }

    public Attempt startAttempt(StartAttemptDto dto, User user) {
        Exam exam = examRepository.findById(dto.getExamId()).orElseThrow();

        Attempt attempt = new Attempt();
        attempt.setExam(exam);
        attempt.setUser(user);
        attempt.setStartTime(LocalDateTime.now());

        return attemptRepository.save(attempt);
    }

    public void saveAnswer(SaveAnswerDto dto) {
        Attempt attempt = attemptRepository.findById(dto.getAttemptId()).orElseThrow();
        Question question = questionRepository.findById(dto.getQuestionId()).orElseThrow();

        Answer answer = new Answer();
        answer.setAttempt(attempt);
        answer.setQuestion(question);
        answer.setSelectedOptionId(dto.getSelectedOptionId());
        answer.setTextAnswer(dto.getTextAnswer());

        answerRepository.save(answer);
    }

    public SubmitAttemptResponseDto submitAttempt(Long attemptId) {
        Attempt attempt = attemptRepository.findById(attemptId).orElseThrow();

        int score = calculateScore(attempt);
        attempt.setScore(score);
        attempt.setEndTime(LocalDateTime.now());

        Attempt saved =  attemptRepository.save(attempt);

        return new SubmitAttemptResponseDto(
                saved.getId(),
                saved.getScore(),
                saved.getStartTime(),
                saved.getEndTime()
        );
    }

    private int calculateScore(Attempt attempt) {
        int score = 0;
        for(Answer answer : attempt.getAnswers()) {
            Question q = answer.getQuestion();

            if (answer.getSelectedOptionId() != null) {
                Option opt = optionRepository.findById(answer.getSelectedOptionId()).orElseThrow();
                if (opt.isCorrect()) {
                    score += q.getPoints();
                } else {
                    score -= q.getNegativePoints();
                }
            }
        }
        return score;
    }
}
