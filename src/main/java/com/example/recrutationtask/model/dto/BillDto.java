package com.example.recrutationtask.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
public class BillDto {

    private ParentDto parent;
    private BigDecimal totalFee;
    private Set<ChildFeeDto> childrenFees;
}
