package com.example.backend.repository;

import com.example.backend.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    java.util.List<Answer> findByAttempt_IdAndQuestion_Id(Long attemptId, Long questionId);
    java.util.List<Answer> findByAttempt_Id(Long attemptId);
}
