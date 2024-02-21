package com.example.recrutationtask.service.impl;

import com.example.recrutationtask.model.command.CreateSchoolCommand;
import com.example.recrutationtask.model.domain.School;
import com.example.recrutationtask.repository.SchoolRepository;
import com.example.recrutationtask.service.SchoolService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository schoolRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<School> findAll(Pageable pageable) {
        return schoolRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public School findById(UUID uuid) {
        return schoolRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("School with id: " + uuid + " not found"));
    }

    @Override
    public School findWithLockingById(UUID uuid) {
        return schoolRepository.findWithLockingById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("School with id: " + uuid + " not found"));
    }

    @Override
    public School addSchool(CreateSchoolCommand command) {
        return schoolRepository.save(School.builder()
                .name(command.getName())
                .hourlyRate(command.getHourPrice())
                .build());
    }
}
