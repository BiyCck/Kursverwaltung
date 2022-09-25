package com.hsba.bi.fitnessstudio.Fitnessstudio.web;

import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Room;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@Setter
public class AppointmentForm {

    @NotNull(message = "Bitte einen Tag auswählen")
    private DayOfWeek dayOfWeek;

    @NotNull(message = "Bitte eine Uhrzeit eingeben")
    private LocalTime localTime;

    @NotNull(message = "Bitte einen Raum auswählen")
    private Room room;

}
