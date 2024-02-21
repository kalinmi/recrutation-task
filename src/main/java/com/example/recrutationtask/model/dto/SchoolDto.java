package com.example.recrutationtask.model.dto;

import com.example.recrutationtask.model.domain.School;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class SchoolDto {

    private UUID id;
    private String name;
    private double hourPrice;

    public static SchoolDto of(School school) {
        return SchoolDto.builder()
                .id(school.getId())
                .name(school.getName())
                .hourPrice(school.getHourlyRate())
                .build();
    }

}
