package com.hsba.bi.fitnessstudio.Fitnessstudio.web;

import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Appointment;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service.AppointmentService;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service.CourseService;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service.DayOfWeekTranslator;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service.RoomService;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.DayOfWeek;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/weekplan")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final UserService userService;
    private final RoomService roomService;
    private final CourseService courseService;

    @GetMapping
    public String index(){
        return "weekplan/index";
    }

    //Anzeigen von Wochenplan
    @GetMapping(path = "/showWeekplan")
    public String show(Model model) {
        model.addAttribute("weekplan", appointmentService.findAll());
        model.addAttribute("trainers", userService.findAllTrainer());
        model.addAttribute("rooms", roomService.findAll());
        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("dayOfWeeks", DayOfWeek.values());
        model.addAttribute("daysOfWeekInGerman", DayOfWeekTranslator.dayOfWeekToGerman());
        return "weekplan/showWeekplan";
    }

    //Hinzufügen von Terminen
    @PostMapping(path = "/showWeekplan")
    public String addAppointment(Appointment appointment) {
        appointmentService.save(appointment);
        return "redirect:/weekplan/showWeekplan";
    }
}
