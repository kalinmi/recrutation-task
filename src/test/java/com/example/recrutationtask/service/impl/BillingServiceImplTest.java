package com.example.recrutationtask.service.impl;

import com.example.recrutationtask.model.domain.Attendance;
import com.example.recrutationtask.model.domain.Child;
import com.example.recrutationtask.model.domain.Parent;
import com.example.recrutationtask.model.domain.School;
import com.example.recrutationtask.model.dto.BillDto;
import com.example.recrutationtask.model.dto.ChildFeeDto;
import com.example.recrutationtask.repository.AttendanceRepository;
import com.example.recrutationtask.service.ParentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class BillingServiceImplTest {

    @Mock
    private ParentService parentService;

    @Mock
    private AttendanceRepository attendanceRepository;

    @InjectMocks
    private BillingServiceImpl billingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculateBillingForParent() {
        UUID parentId = UUID.randomUUID();
        Parent parent = new Parent();
        parent.setId(parentId);

        Child child = new Child();
        child.setFirstName("Test");
        child.setLastName("Child");
        School school = new School();
        school.setHourlyRate(50.00);
        child.setSchool(school);

        Set<Child> children = new HashSet<>();
        children.add(child);
        parent.setChildren(children);

        Attendance attendance = new Attendance();
        attendance.setEntryDate(LocalDateTime.of(2023, 1, 1, 6, 0));
        attendance.setExitDate(LocalDateTime.of(2023, 1, 1, 14, 0));

        Set<Attendance> attendances = new HashSet<>();
        attendances.add(attendance);
        child.setAttendances(attendances);

        when(parentService.findById(parentId)).thenReturn(parent);

        BillDto result = billingService.calculateBillingForParent(parentId);

        assertNotNull(result);
        assertEquals(new BigDecimal("150.0"), result.getTotalFee());
        assertEquals(1, result.getChildrenFees().size());

    }
}