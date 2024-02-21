package com.example.recrutationtask.service;

import com.example.recrutationtask.model.command.CreateSchoolCommand;
import com.example.recrutationtask.model.domain.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface SchoolService {

    Page<School> findAll(Pageable pageable);

    School findById(UUID uuid);

    School findWithLockingById(UUID uuid);

    School addSchool(CreateSchoolCommand command);

}
