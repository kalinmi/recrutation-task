package com.example.recrutationtask.repository;

import com.example.recrutationtask.model.domain.Attendance;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Transactional
    Optional<Attendance> findWithLockingById(UUID uuid);

    @Transactional
    @Modifying
    @Query("update Attendance attendance set attendance.exitDate = :finishDate where attendance.id = :uuid")
    void finishAttendance(@Param("uuid") UUID uuid, @Param("finishDate")LocalDateTime finishDate);
}
