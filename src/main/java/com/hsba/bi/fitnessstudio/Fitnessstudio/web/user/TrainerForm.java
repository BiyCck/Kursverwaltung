package com.hsba.bi.fitnessstudio.Fitnessstudio.web.user;

import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Course;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.DayOfWeek;
import java.util.Set;

@Getter
@Setter
public class TrainerForm {

    @NotNull
    @Size(min = 1, message = "Bitte einen oder mehrere Kurse wählen")
    private Set<Course> courses;

    @NotNull
    @Size(min = 1, message = "Bitte einen oder mehrere Tage auswählen")
    private Set<DayOfWeek> workingDays;

}
