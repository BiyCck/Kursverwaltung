package com.hsba.bi.fitnessstudio.Fitnessstudio.web;

import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service.CourseService;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service.DayOfWeekTranslator;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.Trainer;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.DayOfWeek;

@Controller
@RequestMapping("/weekplan/showTrainer")
@RequiredArgsConstructor
public class TrainerController {

    private final UserService userService;
    private final CourseService courseService;

    //Anzeigen von Trainern
    @GetMapping
    public String show(Model model){
        model.addAttribute("trainers", userService.findAllTrainer());
        model.addAttribute("workingDays", DayOfWeek.values());
        model.addAttribute("workingDaysInGerman", DayOfWeekTranslator.dayOfWeekToGerman());
        model.addAttribute("courses", courseService.findAll());
        return "weekplan/showTrainer";
    }

    //Hinzufügen von Trainern
    @PostMapping(path = "/add")
    public String addEntry(Trainer trainer) {
        userService.save(trainer);
        return "redirect:/weekplan/showTrainer";
    }
}
