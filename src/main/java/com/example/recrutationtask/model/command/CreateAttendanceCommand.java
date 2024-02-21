package com.example.recrutationtask.model.command;


import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateAttendanceCommand {

    private UUID childId;

}
