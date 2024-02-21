package com.example.recrutationtask.service.impl;

import com.example.recrutationtask.model.command.CreateChildCommand;
import com.example.recrutationtask.model.domain.Child;
import com.example.recrutationtask.model.domain.Parent;
import com.example.recrutationtask.model.domain.School;
import com.example.recrutationtask.repository.ChildRepository;
import com.example.recrutationtask.service.ChildService;
import com.example.recrutationtask.service.ParentService;
import com.example.recrutationtask.service.SchoolService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
@Service
@RequiredArgsConstructor
public class ChildServiceImpl implements ChildService {

    private final ChildRepository childRepository;
    private final SchoolService schoolService;
    private final ParentService parentService;

    @Override
    public Page<Child> findAll(Pageable pageable) {
        return childRepository.findAll(pageable);
    }

    @Override
    public Child findById(UUID uuid) {
        return childRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Child with id: " + uuid + " not found"));
    }

    @Override
    public Child findWithLockingById(UUID uuid) {
        return childRepository.findWithLockingById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Child with id: " + uuid + " not found"));
    }

    @Override
    @Transactional
    public Child addChild(CreateChildCommand command) {
        School school = schoolService.findWithLockingById(command.getSchoolId());
        Parent parent = parentService.findWithLockingById(command.getParentId());
        return childRepository.save(Child.builder()
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .school(school)
                .parent(parent)
                .build());
    }
}
