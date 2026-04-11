package com.example.backend.repository;

import com.example.backend.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    Optional<Exam> findByTestLink(String link);
    List<Exam> findByCreatedBy_Id(Long userId);
}
