package com.example.recrutationtask.model.command;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSchoolCommand {

    @NotNull
    private String name;
    @NotNull
    @Min(1)
    private double hourPrice;
}
