package com.example.recrutationtask.model.dto;

import com.example.recrutationtask.model.domain.Child;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ChildDto {

    private UUID id;
    private String firstName;
    private String lastName;
    private UUID parentId;
    private UUID schoolId;


    public static ChildDto of(Child child) {
        return ChildDto.builder()
                .id(child.getId())
                .firstName(child.getFirstName())
                .lastName(child.getLastName())
                .parentId(child.getParent().getId())
                .schoolId(child.getSchool().getId())
                .build();
    }

}
