package com.example.recrutationtask.model.command;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateAttendanceCommand {

    @NotNull
    private UUID childId;

}
