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

    //Anzeigen der Startseite
    @GetMapping
    public String index(){
        return "weekplan/index";
    }

    //Anzeigen des Wochenplans
    @GetMapping(path = "/showWeekplan")
    public String show(Model model) {
        //Alle Termine werden angezeigt
        model.addAttribute("weekplan", appointmentService.findAll());
        //Alle Trainer werden angezeigt
        model.addAttribute("trainers", userService.findAllTrainer());
        //Alle Räume werden angezeigt
        model.addAttribute("rooms", roomService.findAll());
        //Alle Kurse werden angezeigt
        model.addAttribute("courses", courseService.findAll());
        //Alle Wochentage werden anhgezeigt
        model.addAttribute("dayOfWeeks", DayOfWeek.values());
        //Wochentage werden auf Deutsch übersetzt
        model.addAttribute("daysOfWeekInGerman", DayOfWeekTranslator.dayOfWeekToGerman());
        return "weekplan/showWeekplan";
    }

    //Löschen eines Termins
    @PostMapping("/deleteAppointment/{id}")
    public String deleteAppointment(@PathVariable("id") Long id){
        appointmentService.delete(id);
        return "redirect:/weekplan/showWeekplan";
    }

    //Anzeigen des Selectionsscreens für das Auswählen eines Trainers bei neuem Termin
    @GetMapping(path = "/selectTrainer")
    public String showSelectTrainerSite(Model model) {
        model.addAttribute("trainers", userService.findAllTrainer());
        return "weekplan/selectTrainer";
    }

    //Hinzufügen der Trainer-ID, welche in der URL vom AddController abgegriffen wird
    @PostMapping(path = "/selectTrainer")
    public String selectTrainer(Trainer trainer) {
        return "redirect:/weekplan/selectTrainer/" + trainer.getId();
    }


}
