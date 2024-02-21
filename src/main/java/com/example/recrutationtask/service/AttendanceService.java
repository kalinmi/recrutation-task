package com.example.recrutationtask.service;

import com.example.recrutationtask.model.command.CreateAttendanceCommand;
import com.example.recrutationtask.model.domain.Attendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AttendanceService {

    Page<Attendance> findAll(Pageable pageable);

    Attendance findById(UUID uuid);

    Attendance findWithLockingById(UUID uuid);
    Attendance startAttendance(CreateAttendanceCommand command);
    void finishAttendance(UUID uuid);

}
