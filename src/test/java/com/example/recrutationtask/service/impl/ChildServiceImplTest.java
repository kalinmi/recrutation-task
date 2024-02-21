package com.example.recrutationtask.service.impl;

import com.example.recrutationtask.model.command.CreateChildCommand;
import com.example.recrutationtask.model.domain.Child;
import com.example.recrutationtask.model.domain.Parent;
import com.example.recrutationtask.model.domain.School;
import com.example.recrutationtask.repository.ChildRepository;
import com.example.recrutationtask.service.ParentService;
import com.example.recrutationtask.service.SchoolService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ChildServiceImplTest {

    @Mock
    private ChildRepository childRepository;

    @Mock
    private ParentService parentService;

    @Mock
    private SchoolService schoolService;

    @InjectMocks
    private ChildServiceImpl childService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Child> expectedPage = new PageImpl<>(Collections.singletonList(new Child()));
        when(childRepository.findAll(pageable)).thenReturn(expectedPage);

        Page<Child> resultPage = childService.findAll(pageable);

        assertNotNull(resultPage);
        assertEquals(expectedPage, resultPage);
        verify(childRepository).findAll(pageable);
    }

    @Test
    void testFindByIdExists() {
        UUID uuid = UUID.randomUUID();
        Child expectedChild = new Child();
        when(childRepository.findById(uuid)).thenReturn(Optional.of(expectedChild));

        Child result = childService.findById(uuid);

        assertNotNull(result);
        assertEquals(expectedChild, result);
    }

    @Test
    void testFindByIdNotFound() {
        UUID uuid = UUID.randomUUID();
        when(childRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> childService.findById(uuid));
    }

    @Test
    void testFindWithLockingByIdExists() {
        UUID uuid = UUID.randomUUID();
        Child expectedChild = new Child();
        when(childRepository.findWithLockingById(uuid)).thenReturn(Optional.of(expectedChild));

        Child result = childService.findWithLockingById(uuid);

        assertNotNull(result);
        assertEquals(expectedChild, result);
    }

    @Test
    void testFindWithLockingByIdNotFound() {
        UUID uuid = UUID.randomUUID();
        when(childRepository.findWithLockingById(uuid)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> childService.findWithLockingById(uuid));
    }

    @Test
    void testAddChild() {
        CreateChildCommand command = new CreateChildCommand();
        command.setFirstName("John");
        command.setLastName("Doe");
        command.setSchoolId(UUID.randomUUID());
        command.setParentId(UUID.randomUUID());

        School school = new School();
        Parent parent = new Parent();

        when(schoolService.findWithLockingById(command.getSchoolId())).thenReturn(school);
        when(parentService.findWithLockingById(command.getParentId())).thenReturn(parent);
        when(childRepository.save(any(Child.class))).thenAnswer(i -> i.getArguments()[0]);

        Child savedChild = childService.addChild(command);

        assertNotNull(savedChild);
        assertEquals(command.getFirstName(), savedChild.getFirstName());
        assertEquals(command.getLastName(), savedChild.getLastName());
        assertEquals(school, savedChild.getSchool());
        assertEquals(parent, savedChild.getParent());

        verify(childRepository).save(any(Child.class));
    }

}