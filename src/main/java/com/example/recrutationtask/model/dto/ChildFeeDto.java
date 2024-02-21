package com.example.recrutationtask.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class ChildFeeDto {

    private String childName;
    private long timeInSchool;
    private BigDecimal fee;
}
