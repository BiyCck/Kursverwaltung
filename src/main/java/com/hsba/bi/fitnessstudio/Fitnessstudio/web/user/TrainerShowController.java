package com.hsba.bi.fitnessstudio.Fitnessstudio.web.user;

import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Course;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service.CourseService;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service.DayOfWeekTranslator;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.Trainer;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.UserService;
import com.hsba.bi.fitnessstudio.Fitnessstudio.web.appointment.AppointmentFormConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.DayOfWeek;
import java.util.List;

@Controller
@RequestMapping("/user/showTrainer")
@RequiredArgsConstructor
public class TrainerShowController {

    private final UserService userService;
    private final CourseService courseService;
    private final TrainerFormConverter formConverter;

    @ModelAttribute("trainers")
    public List<Trainer> getAllTrainer(){
        return userService.findAllTrainer();
    }

    @ModelAttribute("workingDays")
    public DayOfWeek[] getWorkingDays(){
        return DayOfWeek.values();
    }

    @ModelAttribute("workingDaysInGerman")
    public String[] getWorkingDaysInGerman(){
        return DayOfWeekTranslator.dayOfWeekToGerman();
    }

    @ModelAttribute("courses")
    public List<Course> getCourses(){
        return courseService.findAll();
    }

    @GetMapping
    public String show(Model model){
        return "user/showTrainer";
    }

}
