package com.example.recrutationtask.service.impl;

import com.example.recrutationtask.model.command.CreateSchoolCommand;
import com.example.recrutationtask.model.domain.School;
import com.example.recrutationtask.repository.SchoolRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SchoolServiceImplTest {

    @Mock
    private SchoolRepository schoolRepository;

    @InjectMocks
    private SchoolServiceImpl schoolService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<School> expectedPage = new PageImpl<>(Collections.singletonList(new School()));
        when(schoolRepository.findAll(pageable)).thenReturn(expectedPage);

        Page<School> resultPage = schoolService.findAll(pageable);

        assertNotNull(resultPage);
        assertEquals(expectedPage, resultPage);
        verify(schoolRepository).findAll(pageable);
    }

    @Test
    void testFindByIdExists() {
        UUID uuid = UUID.randomUUID();
        School expectedSchool = new School();
        when(schoolRepository.findById(uuid)).thenReturn(Optional.of(expectedSchool));

        School result = schoolService.findById(uuid);

        assertNotNull(result);
        assertEquals(expectedSchool, result);
    }

    @Test
    void testFindByIdNotFound() {
        UUID uuid = UUID.randomUUID();
        when(schoolRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> schoolService.findById(uuid));
    }

    @Test
    void testFindWithLockingByIdExists() {
        UUID uuid = UUID.randomUUID();
        School expectedSchool = new School();
        when(schoolRepository.findWithLockingById(uuid)).thenReturn(Optional.of(expectedSchool));

        School result = schoolService.findWithLockingById(uuid);

        assertNotNull(result);
        assertEquals(expectedSchool, result);
    }

    @Test
    void testFindWithLockingByIdNotFound() {
        UUID uuid = UUID.randomUUID();
        when(schoolRepository.findWithLockingById(uuid)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> schoolService.findById(uuid));
    }

    @Test
    void testAddSchool() {
        // given
        CreateSchoolCommand command = new CreateSchoolCommand();
        command.setName("Test School");
        command.setHourPrice(100.0);

        School expectedSchool = School.builder()
                .name(command.getName())
                .hourlyRate(command.getHourPrice())
                .build();

        // when
        when(schoolRepository.save(Mockito.any(School.class))).thenReturn(expectedSchool);
        School savedSchool = schoolService.addSchool(command);

        // then
        assertNotNull(savedSchool);
        assertEquals(expectedSchool.getName(), savedSchool.getName());
        assertEquals(expectedSchool.getHourlyRate(), savedSchool.getHourlyRate());
        verify(schoolRepository).save(Mockito.any(School.class));
    }

}