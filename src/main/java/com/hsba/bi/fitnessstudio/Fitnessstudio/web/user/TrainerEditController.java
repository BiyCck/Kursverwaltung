package com.hsba.bi.fitnessstudio.Fitnessstudio.web.user;

import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Course;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service.CourseService;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service.DayOfWeekTranslator;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.Trainer;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.DayOfWeek;
import java.util.List;

@Controller
@RequestMapping("/user/editTrainer/{id}")
@RequiredArgsConstructor
public class TrainerEditController {

    private final UserService userService;
    private final CourseService courseService;
    private final TrainerFormConverter formConverter;


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

    @ModelAttribute("trainer")
    public Trainer getTrainer(@PathVariable("id") Long id){
        return userService.getTrainer(id);
    }

    @GetMapping
    public String showEditTrainerSite(@PathVariable("id") Long id, Model model){
        TrainerForm trainerForm = formConverter.toForm(userService.getTrainer(id));
        model.addAttribute("trainerForm", trainerForm);
        return "user/editTrainer";
    }

    @PostMapping
    public String editTrainer(@PathVariable("id") Long id, @ModelAttribute("trainerForm") @Valid TrainerForm trainerForm, BindingResult trainerBinding, Model model){
        if (trainerBinding.hasErrors()){
            model.addAttribute("trainerForm", trainerForm);
            return "user/editTrainer";
        }
        Trainer trainer = formConverter.update(userService.getTrainer(id), trainerForm);
        userService.save(trainer);
        return "redirect:/user/showTrainerSite";
    }

}
