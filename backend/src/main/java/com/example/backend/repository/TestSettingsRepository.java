package com.example.backend.repository;


import com.example.backend.entity.TestSettings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TestSettingsRepository extends JpaRepository<TestSettings, Long> {
    Optional<TestSettings> findByExamId(Long examId);
}


