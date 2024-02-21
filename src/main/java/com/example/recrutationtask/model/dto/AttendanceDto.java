package com.example.recrutationtask.model.dto;

import com.example.recrutationtask.model.domain.Attendance;
import com.example.recrutationtask.model.domain.Child;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class AttendanceDto {

    private UUID id;
    private UUID childId;
    private LocalDateTime entryDate;
    private LocalDateTime exitDate;

    public static AttendanceDto of(Attendance attendance) {
        return AttendanceDto.builder()
                .id(attendance.getId())
                .childId(attendance.getChild().getId())
                .entryDate(attendance.getEntryDate())
                .exitDate(attendance.getExitDate())
                .build();
    }

}
