package com.example.recrutationtask.service;

import com.example.recrutationtask.model.command.CreateParentCommand;
import com.example.recrutationtask.model.domain.Parent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ParentService {

    Page<Parent> findAll(Pageable pageable);

    Parent findById(UUID uuid);

    Parent addParent(CreateParentCommand command);

    Parent findWithLockingById(UUID uuid);

}
