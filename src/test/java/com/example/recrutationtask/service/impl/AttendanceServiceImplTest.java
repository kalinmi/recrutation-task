package com.example.recrutationtask.service.impl;

import com.example.recrutationtask.model.command.CreateAttendanceCommand;
import com.example.recrutationtask.model.domain.Attendance;
import com.example.recrutationtask.model.domain.Child;
import com.example.recrutationtask.repository.AttendanceRepository;
import com.example.recrutationtask.service.ChildService;
import com.example.recrutationtask.service.provider.DateProvider;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AttendanceServiceImplTest {


    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private ChildService childService;

    @Mock
    private DateProvider dateProvider;

    @InjectMocks
    private AttendanceServiceImpl attendanceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Attendance> expectedPage = new PageImpl<>(Collections.singletonList(new Attendance()));
        when(attendanceRepository.findAll(pageable)).thenReturn(expectedPage);

        Page<Attendance> resultPage = attendanceService.findAll(pageable);

        assertNotNull(resultPage);
        assertEquals(expectedPage, resultPage);
        verify(attendanceRepository).findAll(pageable);
    }

    @Test
    void testFindByIdExists() {
        UUID uuid = UUID.randomUUID();
        Attendance expectedAttendance = new Attendance();
        when(attendanceRepository.findById(uuid)).thenReturn(Optional.of(expectedAttendance));

        Attendance result = attendanceService.findById(uuid);

        assertNotNull(result);
        assertEquals(expectedAttendance, result);
    }

    @Test
    void testFindByIdNotFound() {
        UUID uuid = UUID.randomUUID();
        when(attendanceRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> attendanceService.findById(uuid));
    }

    @Test
    void testFindWithLockingByIdExists() {
        UUID uuid = UUID.randomUUID();
        Attendance expectedAttendance = new Attendance();
        when(attendanceRepository.findWithLockingById(uuid)).thenReturn(Optional.of(expectedAttendance));

        Attendance result = attendanceService.findWithLockingById(uuid);

        assertNotNull(result);
        assertEquals(expectedAttendance, result);
    }

    @Test
    void testFindWithLockingByIdNotFound() {
        UUID uuid = UUID.randomUUID();
        when(attendanceRepository.findWithLockingById(uuid)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> attendanceService.findWithLockingById(uuid));
    }

    @Test
    void testStartAttendance() {
        UUID childId = UUID.randomUUID();
        CreateAttendanceCommand command = new CreateAttendanceCommand();
        command.setChildId(childId);
        Child child = new Child();
        child.setId(childId);
        LocalDateTime now = LocalDateTime.now();
        Attendance expectedAttendance = Attendance.builder()
                .id(UUID.randomUUID())
                .child(child)
                .entryDate(now)
                .build();

        when(childService.findWithLockingById(childId)).thenReturn(child);
        when(dateProvider.provideNow()).thenReturn(now);
        when(attendanceRepository.saveAndFlush(any(Attendance.class))).thenReturn(expectedAttendance);

        Attendance startedAttendance = attendanceService.startAttendance(command);

        assertNotNull(startedAttendance);
        assertEquals(child, startedAttendance.getChild());
        assertEquals(now, startedAttendance.getEntryDate());
    }

    @Test
    void testFinishAttendance() {
        UUID attendanceId = UUID.randomUUID();
        Attendance attendanceToFinish = new Attendance();
        LocalDateTime now = LocalDateTime.now();

        when(attendanceRepository.findWithLockingById(attendanceId)).thenReturn(Optional.of(attendanceToFinish));
        when(dateProvider.provideNow()).thenReturn(now);
        doNothing().when(attendanceRepository).finishAttendance(attendanceId, now);

        attendanceService.finishAttendance(attendanceId);

        verify(attendanceRepository).finishAttendance(attendanceId, now);
    }
}