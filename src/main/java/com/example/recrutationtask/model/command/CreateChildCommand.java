package com.example.recrutationtask.model.command;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateChildCommand {

    @NotNull(message = "Firstname can not be null")
    @Pattern(regexp = "[A-Z][a-z]+", message = "Firstname in wrong format")
    private String firstName;
    @NotNull(message = "Lastname can not be null")
    @Pattern(regexp = "[A-Z][a-z]+", message = "LastName in wrong format")
    private String lastName;
    private UUID parentId;
    private UUID schoolId;
}
