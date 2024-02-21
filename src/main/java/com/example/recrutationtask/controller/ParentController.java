package com.example.recrutationtask.controller;

import com.example.recrutationtask.model.command.CreateParentCommand;
import com.example.recrutationtask.model.domain.Parent;
import com.example.recrutationtask.model.dto.ParentDto;
import com.example.recrutationtask.service.ParentService;
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
@RequestMapping("/api/v1/parent")
@RequiredArgsConstructor
@Slf4j
public class ParentController {

    private final ParentService parentService;

    @GetMapping
    public ResponseEntity<Page<ParentDto>> getAllParents(@PageableDefault Pageable pageable) {
        log.info("getAllParents({})", pageable);
        List<ParentDto> list = parentService.findAll(pageable).stream()
                .map(ParentDto::of)
                .collect(Collectors.toList());
        Page<ParentDto> parentsDtos = new PageImpl<>(list, pageable, list.size());
        return ResponseEntity.ok(parentsDtos);
    }

    @PostMapping
    public ResponseEntity<ParentDto> addParent(@RequestBody @Valid CreateParentCommand command) {
        log.info("addParent({})", command);
        Parent parent = parentService.addParent(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(ParentDto.of(parent));
    }

}
