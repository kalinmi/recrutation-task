package com.example.recrutationtask.service;

import com.example.recrutationtask.model.command.CreateChildCommand;
import com.example.recrutationtask.model.domain.Child;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ChildService {

    Page<Child> findAll(Pageable pageable);

    Child findById(UUID uuid);

    Child findWithLockingById(UUID uuid);
    Child addChild(CreateChildCommand command);

}
