package com.example.backend.service;

import com.example.backend.dto.ResultResponseDto;
import com.example.backend.entity.Attempt;
import com.example.backend.repository.AttemptRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResultService {
    private final AttemptRepository attemptRepository;

    public ResultService(AttemptRepository attemptRepository) {
        this.attemptRepository = attemptRepository;
    }

    public List<ResultResponseDto> getResultsByExam(Long examId) {
        List<Attempt> attempts = attemptRepository.findByExamIdWithUser(examId);

        List<ResultResponseDto> results = new ArrayList<>();

        for (Attempt a : attempts) {
            String studentName = a.getUser() != null ? a.getUser().getName() : null;
            String studentEmail = a.getUser() != null ? a.getUser().getEmail() : null;
            results.add(new ResultResponseDto(
                    a.getId(),
                    a.getUser().getName(),
                    studentEmail,
                    a.getScore(),
                    a.getStartTime(),
                    a.getEndTime()
            ));
//            System.out.println("Attempt ID: " + a.getId());
//            System.out.println("User object: " + a.getUser());
//            System.out.println("User name from entity: " + a.getUser().getName());
        }
        return results;
    }
}
