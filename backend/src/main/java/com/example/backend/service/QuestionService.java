package com.example.backend.service;

import com.example.backend.dto.AddQuestionDTO;
import com.example.backend.dto.OptionDto;
import com.example.backend.entity.Exam;
import com.example.backend.entity.Option;
import com.example.backend.entity.Question;
import com.example.backend.repository.ExamRepository;
import com.example.backend.repository.OptionRepository;
import com.example.backend.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final ExamRepository examRepository;
    private final OptionRepository optionRepository;

    public QuestionService(QuestionRepository questionRepository,
                           ExamRepository examRepository,
                           OptionRepository optionRepository) {
        this.questionRepository = questionRepository;
        this.examRepository = examRepository;
        this.optionRepository = optionRepository;
    }
    public Question addQuestion(AddQuestionDTO dto) {
        Exam exam = examRepository.findById(dto.getExamId()).orElseThrow();

        Question question = new Question();
        question.setText(dto.getText());
        question.setType(dto.getType());
        question.setPoints(dto.getPoints());
        question.setNegativePoints(dto.getNegativePoints());
        question.setExam(exam);

        Question savedQuestion = questionRepository.save(question);

        if(dto.getOptions() != null) {
            for (OptionDto opt : dto.getOptions()) {
                Option option = new Option();
                option.setText(opt.getText());
                option.setCorrect(opt.isCorrect());
                option.setQuestion(savedQuestion);
                optionRepository.save(option);
            }
        }
        return savedQuestion;
    }
    public List<Question> getQuestionsByExam(Long examId) {
        return questionRepository.findByExamId(examId);
    }
}
