package com.example.recrutationtask.service.impl;

import com.example.recrutationtask.model.command.CreateAttendanceCommand;
import com.example.recrutationtask.model.domain.Attendance;
import com.example.recrutationtask.model.domain.Child;
import com.example.recrutationtask.repository.AttendanceRepository;
import com.example.recrutationtask.service.AttendanceService;
import com.example.recrutationtask.service.ChildService;
import com.example.recrutationtask.service.provider.DateProvider;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final ChildService childService;
    private final DateProvider dateProvider;

    @Override
    public Page<Attendance> findAll(Pageable pageable) {
        return attendanceRepository.findAll(pageable);
    }

    @Override
    public Attendance findById(UUID uuid) {
        return attendanceRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Attendance with id: " + uuid + " not found"));
    }

    @Override
    public Attendance findWithLockingById(UUID uuid) {
        return attendanceRepository.findWithLockingById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Attendance with id: " + uuid + " not found"));
    }

    @Override
    public Attendance startAttendance(CreateAttendanceCommand command) {
        Child child = childService.findWithLockingById(command.getChildId());
        return attendanceRepository.saveAndFlush(Attendance.builder()
                .child(child)
                .entryDate(dateProvider.provideNow())
                .build());
    }

    @Override
    @Transactional
    public void finishAttendance(UUID uuid) {
        Attendance attendanceToFinish = attendanceRepository.findWithLockingById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Attendance with id: " + uuid + " not found"));
        attendanceRepository.finishAttendance(uuid, dateProvider.provideNow());
    }
}
