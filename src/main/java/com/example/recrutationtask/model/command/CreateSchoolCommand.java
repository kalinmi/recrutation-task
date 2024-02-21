package com.example.recrutationtask.model.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSchoolCommand {
    private String name;
    private double hourPrice;
}
