package com.example.backend.repository;

import com.example.backend.entity.Violation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViolationRepository extends JpaRepository<Violation, Long> {
    java.util.List<Violation> findByAttempt_Id(Long attemptId);
}
