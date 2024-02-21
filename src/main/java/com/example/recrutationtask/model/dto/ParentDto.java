package com.example.recrutationtask.model.dto;

import com.example.recrutationtask.model.domain.Parent;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;
@Data
@Builder
public class ParentDto {

    private UUID id;
    private String firstName;
    private String lastName;

    public static ParentDto of(Parent parent) {
        return ParentDto.builder()
                .id(parent.getId())
                .firstName(parent.getFirstName())
                .lastName(parent.getLastName())
                .build();
    }

}
