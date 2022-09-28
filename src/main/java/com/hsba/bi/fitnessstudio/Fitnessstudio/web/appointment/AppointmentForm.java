package com.hsba.bi.fitnessstudio.Fitnessstudio.web.appointment;

import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Course;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Room;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @NotNull(message = "Bitte einen Kurs auswählen")
    private Course course;

}
