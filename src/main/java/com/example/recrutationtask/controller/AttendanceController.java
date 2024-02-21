package com.example.recrutationtask.controller;

import com.example.recrutationtask.model.command.CreateAttendanceCommand;
import com.example.recrutationtask.model.domain.Attendance;
import com.example.recrutationtask.model.dto.AttendanceDto;
import com.example.recrutationtask.service.AttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/attendance")
@RequiredArgsConstructor
@Slf4j
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping
    public ResponseEntity<Page<AttendanceDto>> getAllAttendances(@PageableDefault Pageable pageable) {
        log.info("getAllAttendances({}", pageable);
        List<AttendanceDto> list = attendanceService.findAll(pageable).stream()
                .map(AttendanceDto::of)
                .collect(Collectors.toList());
        Page<AttendanceDto> attendancesDtos = new PageImpl<>(list, pageable, list.size());
        return ResponseEntity.ok(attendancesDtos);
    }

    @PostMapping
    public ResponseEntity<AttendanceDto> startAttendance(@RequestBody @Valid CreateAttendanceCommand command) {
        log.info("addAuthor({})", command);
        Attendance attendance = attendanceService.startAttendance(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(AttendanceDto.of(attendance));
    }

    @PatchMapping(value = "/{uuid}")
    public ResponseEntity<Void> finishAttendance(@PathVariable("uuid") UUID uuid) {
        log.info("finishAtendance({})", uuid);
        attendanceService.finishAttendance(uuid);
        return ResponseEntity.ok().build();
    }
}
