package com.hsba.bi.fitnessstudio.Fitnessstudio.web;

import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Appointment;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service.AppointmentService;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service.CourseService;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service.DayOfWeekTranslator;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service.RoomService;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.Trainer;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(path = "/showWeekplan")
    public String selectTrainer(Trainer trainer) {
        return "redirect:/weekplan/showWeekplan/" + trainer.getId();
    }

    @GetMapping(path = "/showWeekplan/{id}")
    public String showAddAppointmentSite(@PathVariable("id") Long id, Model model){
        Trainer trainer = getTrainer(id);
        model.addAttribute("trainer", trainer);
        model.addAttribute("rooms", roomService.findAll());
        model.addAttribute("courses", trainer.getCourses());
        model.addAttribute("dayOfWeeks", trainer.getWorkingDays());
        model.addAttribute("daysOfWeekInGerman", DayOfWeekTranslator.dayOfWeekToGerman());
        return "weekplan/addAppointment";
    }

    private Trainer getTrainer(Long id) {
        Trainer trainer = userService.getTrainer(id);
        if (trainer == null){
            throw new NotFoundException();
        }
        return trainer;
    }

    @PostMapping(path = "/showWeekplan/{id}")
    public String addAppointment(@PathVariable("id") Long id, Appointment appointment){
        appointment.setTrainer(getTrainer(id));
        appointmentService.save(appointment);
        return "redirect:/weekplan/showWeekplan";
    }

    @ExceptionHandler(NotFoundException.class)
    public String notFound(){
        return "weekplan/trainerNotFound";
    }

}
