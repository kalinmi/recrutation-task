package com.example.recrutationtask.repository;

import com.example.recrutationtask.model.domain.Parent;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface ParentRepository extends JpaRepository<Parent, UUID> {

    @Lock(LockModeType.WRITE)
    @Transactional
    Optional<Parent> findWithLockingById(UUID uuid);
}
