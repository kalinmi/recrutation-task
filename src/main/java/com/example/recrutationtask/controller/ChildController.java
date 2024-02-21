package com.example.recrutationtask.controller;

import com.example.recrutationtask.model.command.CreateChildCommand;
import com.example.recrutationtask.model.domain.Child;
import com.example.recrutationtask.model.dto.ChildDto;
import com.example.recrutationtask.service.ChildService;
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
@RequestMapping("/api/v1/child")
@RequiredArgsConstructor
@Slf4j
public class ChildController {

    private final ChildService childService;

    @GetMapping
    public ResponseEntity<Page<ChildDto>> getAllChildren(@PageableDefault Pageable pageable) {
        log.info("getAllChildren({})", pageable);
        List<ChildDto> list = childService.findAll(pageable).stream()
                .map(ChildDto::of)
                .collect(Collectors.toList());
        Page<ChildDto> childrenDtos = new PageImpl<>(list, pageable, list.size());
        return ResponseEntity.ok(childrenDtos);
    }

    @PostMapping
    public ResponseEntity<ChildDto> addChild(@RequestBody @Valid CreateChildCommand command) {
        log.info("addChild({})", command);
        Child child = childService.addChild(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(ChildDto.of(child));
    }
}
