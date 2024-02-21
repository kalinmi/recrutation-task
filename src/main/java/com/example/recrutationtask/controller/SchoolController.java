package com.example.recrutationtask.controller;

import com.example.recrutationtask.model.command.CreateSchoolCommand;
import com.example.recrutationtask.model.domain.School;
import com.example.recrutationtask.model.dto.SchoolDto;
import com.example.recrutationtask.service.SchoolService;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/school")
@RequiredArgsConstructor
@Slf4j
public class SchoolController {

    private final SchoolService schoolService;

    @GetMapping
    public ResponseEntity<Page<SchoolDto>> getAllSchools(@PageableDefault Pageable pageable) {
        log.info("getAllSchools({})", pageable);
        List<SchoolDto> list = schoolService.findAll(pageable).stream()
                .map(SchoolDto::of)
                .collect(Collectors.toList());
        Page<SchoolDto> schoolDtos = new PageImpl<>(list, pageable, list.size());
        return ResponseEntity.ok(schoolDtos);
    }

    @PostMapping
    public ResponseEntity<SchoolDto> addSchool(@RequestBody @Valid CreateSchoolCommand command) {
        log.info("addSchool({})", command);
        School school = schoolService.addSchool(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(SchoolDto.of(school));
    }

}
