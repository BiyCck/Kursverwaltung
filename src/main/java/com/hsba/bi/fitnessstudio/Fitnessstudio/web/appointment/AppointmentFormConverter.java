package com.hsba.bi.fitnessstudio.Fitnessstudio.web.appointment;

import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Appointment;
import org.springframework.stereotype.Component;

@Component
public class AppointmentFormConverter {

    AppointmentForm toForm(Appointment appointment){
        AppointmentForm form = new AppointmentForm();
        form.setDayOfWeek(appointment.getDayOfWeek());
        form.setLocalTime(appointment.getLocalTime());
        form.setRoom(appointment.getRoom());
        form.setCourse(appointment.getCourse());
        return form;
    }

    Appointment update(Appointment appointment, AppointmentForm form){
        appointment.setDayOfWeek(form.getDayOfWeek());
        appointment.setLocalTime(form.getLocalTime());
        appointment.setRoom(form.getRoom());
        appointment.setCourse(form.getCourse());
        return appointment;
    }

}
