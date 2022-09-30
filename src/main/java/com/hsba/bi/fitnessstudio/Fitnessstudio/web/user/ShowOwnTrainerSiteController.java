package com.hsba.bi.fitnessstudio.Fitnessstudio.web.user;

import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Appointment;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.Trainer;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.User;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Set;

@Controller
@RequestMapping("/user/showTrainerSite")
@RequiredArgsConstructor
public class ShowOwnTrainerSiteController {

    private final UserService userService;

    @ModelAttribute("weekplan")
    public Set<Appointment> getAppointmentsFromTrainer(){
        Trainer trainer = userService.getTrainerByName(User.getCurrentUsername());
        return trainer.getAppointments();
    }

    @ModelAttribute("trainer")
    public Trainer getTrainer(){
        Trainer trainer = userService.getTrainerByName(User.getCurrentUsername());
        return trainer;
    }

    @GetMapping
    public String show(){
        return "user/showTrainerSite";
    }
}
