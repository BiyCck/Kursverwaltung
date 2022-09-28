package com.hsba.bi.fitnessstudio.Fitnessstudio.web.appointment;

import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service.AppointmentService;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service.CourseService;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service.DayOfWeekTranslator;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service.RoomService;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.Trainer;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/weekplan")
public class AppointmentShowController {

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

    @PostMapping("/deleteAppointment/{id}")
    public String deleteAppointment(@PathVariable("id") Long id){
        appointmentService.delete(id);
        return "redirect:/weekplan/showWeekplan";
    }

    @GetMapping(path = "/selectTrainer")
    public String showSelectTrainerSite(Model model) {
        model.addAttribute("trainers", userService.findAllTrainer());
        return "weekplan/selectTrainer";
    }

    @PostMapping(path = "/selectTrainer")
    public String selectTrainer(Trainer trainer) {
        return "redirect:/weekplan/selectTrainer/" + trainer.getId();
    }


}
