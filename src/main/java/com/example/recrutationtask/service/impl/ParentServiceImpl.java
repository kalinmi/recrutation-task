package com.example.recrutationtask.service.impl;

import com.example.recrutationtask.model.command.CreateParentCommand;
import com.example.recrutationtask.model.domain.Parent;
import com.example.recrutationtask.repository.ParentRepository;
import com.example.recrutationtask.service.ParentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParentServiceImpl implements ParentService {

    private final ParentRepository parentRepository;

    @Override
    public Page<Parent> findAll(Pageable pageable) {
        return parentRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Parent findById(UUID uuid) {
        return parentRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Child with id: " + uuid + " not found"));
    }

    @Override
    public Parent findWithLockingById(UUID uuid) {
        return parentRepository.findWithLockingById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Child with id: " + uuid + " not found"));
    }

    @Override
    public Parent addParent(CreateParentCommand command) {
        return parentRepository.save(Parent.builder()
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .build());
    }
}
