package com.example.recrutationtask.repository;

import com.example.recrutationtask.model.domain.School;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface SchoolRepository extends JpaRepository<School, UUID> {

    @Lock(LockModeType.WRITE)
    @Transactional
    Optional<School> findWithLockingById(UUID uuid);

}
