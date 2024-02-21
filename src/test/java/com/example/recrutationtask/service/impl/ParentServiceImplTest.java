package com.example.recrutationtask.service.impl;

import com.example.recrutationtask.model.command.CreateParentCommand;
import com.example.recrutationtask.model.domain.Parent;
import com.example.recrutationtask.repository.ParentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ParentServiceImplTest {

    @Mock
    private ParentRepository parentRepository;

    @InjectMocks
    private ParentServiceImpl parentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Parent> expectedPage = new PageImpl<>(Collections.singletonList(new Parent()));
        when(parentRepository.findAll(pageable)).thenReturn(expectedPage);

        Page<Parent> resultPage = parentService.findAll(pageable);

        assertNotNull(resultPage);
        assertEquals(expectedPage, resultPage);
        verify(parentRepository).findAll(pageable);
    }

    @Test
    void testFindByIdExists() {
        UUID uuid = UUID.randomUUID();
        Parent expectedParent = new Parent();
        when(parentRepository.findById(uuid)).thenReturn(Optional.of(expectedParent));

        Parent result = parentService.findById(uuid);

        assertNotNull(result);
        assertEquals(expectedParent, result);
    }

    @Test
    void testFindByIdNotFound() {
        UUID uuid = UUID.randomUUID();
        when(parentRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> parentService.findById(uuid));
    }

    @Test
    void testFindWithLockingByIdExists() {
        UUID uuid = UUID.randomUUID();
        Parent expectedParent = new Parent();
        when(parentRepository.findWithLockingById(uuid)).thenReturn(Optional.of(expectedParent));

        Parent result = parentService.findWithLockingById(uuid);

        assertNotNull(result);
        assertEquals(expectedParent, result);
    }

    @Test
    void testFindWithLockingByIdNotFound() {
        UUID uuid = UUID.randomUUID();
        when(parentRepository.findWithLockingById(uuid)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> parentService.findWithLockingById(uuid));
    }

    @Test
    void testAddParent() {
        //given
        CreateParentCommand command = new CreateParentCommand();
        command.setFirstName("John");
        command.setLastName("Doe");

        Parent expectedParent = Parent.builder()
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .build();

        //when
        when(parentRepository.save(Mockito.any(Parent.class))).thenReturn(expectedParent);
        Parent savedParent = parentService.addParent(command);

        //then
        assertNotNull(savedParent);
        assertEquals(expectedParent.getFirstName(), savedParent.getFirstName());
        assertEquals(expectedParent.getLastName(), savedParent.getLastName());
        verify(parentRepository).save(Mockito.any(Parent.class));
    }


}