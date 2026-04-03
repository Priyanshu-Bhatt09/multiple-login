package com.example.backend.repository;

import com.example.backend.entity.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AttemptRepository extends JpaRepository<Attempt, Long> {
    List<Attempt> findByExamId(Long examId);

    @Query("SELECT a FROM Attempt a JOIN FETCH a.user WHERE a.exam.id = :examId")
    List<Attempt> findByExamIdWithUser(Long examId);
}
