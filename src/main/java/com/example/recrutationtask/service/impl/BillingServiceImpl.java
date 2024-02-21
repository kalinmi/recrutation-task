package com.example.recrutationtask.service.impl;

import com.example.recrutationtask.model.domain.Attendance;
import com.example.recrutationtask.model.domain.Child;
import com.example.recrutationtask.model.domain.Parent;
import com.example.recrutationtask.model.dto.BillDto;
import com.example.recrutationtask.model.dto.ChildFeeDto;
import com.example.recrutationtask.model.dto.ParentDto;
import com.example.recrutationtask.service.BillingService;
import com.example.recrutationtask.service.ParentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {
    private final ParentService parentService;

    @Override
    @Transactional
    public BillDto calculateBillingForParent(UUID parentId) {
        Parent parent = parentService.findById(parentId);
        Set<Child> children = parent.getChildren();

        BigDecimal totalFee = BigDecimal.ZERO;
        Set<ChildFeeDto> childFees = new HashSet<>();
        long hoursOutsideFreePeriod = 0;

        for (Child child : children) {
            Set<Attendance> attendances = child.getAttendances();
            BigDecimal childTotalFee = BigDecimal.ZERO;

            for (Attendance attendance : attendances) {
                LocalDateTime start = attendance.getEntryDate();
                LocalDateTime end = attendance.getExitDate();
                hoursOutsideFreePeriod = calculateHoursOutsideFreePeriod(start, end);
                BigDecimal fee = calculateFeeForHours(hoursOutsideFreePeriod, child.getSchool().getHourlyRate());
                childTotalFee = childTotalFee.add(fee);
            }

            totalFee = totalFee.add(childTotalFee);
            childFees.add(new ChildFeeDto(child.getFirstName() + " " + child.getLastName(), hoursOutsideFreePeriod, childTotalFee));
        }
        ParentDto parentDto = ParentDto.of(parent);

        return new BillDto(parentDto, totalFee, childFees);
    }


    private long calculateHoursOutsideFreePeriod(LocalDateTime start, LocalDateTime end) {
        LocalDateTime freePeriodStart = LocalDateTime.of(start.toLocalDate(), LocalTime.of(7, 0));
        LocalDateTime freePeriodEnd = LocalDateTime.of(start.toLocalDate(), LocalTime.of(12, 0));

        long hoursBeforeFreePeriod = 0;
        long hoursAfterFreePeriod = 0;
        if (start.isBefore(freePeriodStart)) {
            hoursBeforeFreePeriod = ChronoUnit.HOURS.between(start, freePeriodStart);
            hoursBeforeFreePeriod += (ChronoUnit.MINUTES.between(start, freePeriodStart) % 60 == 0 ? 0 : 1);
        }
        if (end.isAfter(freePeriodEnd)) {
            hoursAfterFreePeriod = ChronoUnit.HOURS.between(freePeriodEnd, end);
            hoursAfterFreePeriod += (ChronoUnit.MINUTES.between(freePeriodEnd, end) % 60 == 0 ? 0 : 1);
        }
        return hoursBeforeFreePeriod + hoursAfterFreePeriod;
    }

    private BigDecimal calculateFeeForHours(long hoursOutsideFreePeriod, double hourPrice) {
        return BigDecimal.valueOf(hoursOutsideFreePeriod * hourPrice);
    }


}
