package com.hsba.bi.fitnessstudio.Fitnessstudio.web.appointment;

import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Appointment;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Course;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Room;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service.AppointmentService;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service.DayOfWeekTranslator;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service.RoomService;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.Trainer;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.UserService;
import com.hsba.bi.fitnessstudio.Fitnessstudio.web.TrainerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "weekplan/selectTrainer/{id}")
public class AppointmentAddController {

    private final AppointmentService appointmentService;
    private final RoomService roomService;
    private final UserService userService;
    private final AppointmentFormConverter formConverter;

    @ModelAttribute("trainer")
    public Trainer getTrainerForModel(@PathVariable("id") Long id){
        return userService.getTrainer(id);
    }
    @ModelAttribute("rooms")
    public List<Room> getRooms(){
        return roomService.findAll();
    }

    @ModelAttribute("courses")
    public Set<Course> getCourses(@PathVariable("id") Long id){
        Trainer trainer = getTrainer(id);
        return trainer.getCourses();
    }

    @ModelAttribute("dayOfWeeks")
    public Set<DayOfWeek> getDayOfWeeks(@PathVariable("id") Long id){
        Trainer trainer = getTrainer(id);
        return trainer.getWorkingDays();
    }

    @ModelAttribute("daysOfWeekInGerman")
    public String[] getDaysOfWeekInGerman(){
        return DayOfWeekTranslator.dayOfWeekToGerman();
    }

    @GetMapping
    public String showAddAppointmentSite(Model model){
        model.addAttribute("appointmentForm", new AppointmentForm());
        return "weekplan/addAppointment";
    }

    @PostMapping
    public String addAppointment(@PathVariable("id") Long id, @ModelAttribute("appointmentForm") @Valid AppointmentForm appointmentForm, BindingResult appointmentBinding, Model model){
        if (appointmentBinding.hasErrors()){
            model.addAttribute("appointmentForm", appointmentForm);
            return "weekplan/addAppointment";
        }
        Appointment appointment = new Appointment();
        appointment.setTrainer(userService.getTrainer(id));
        appointmentService.save(formConverter.update(appointment, appointmentForm));
        return "redirect:/weekplan/showWeekplan";
    }

    private Trainer getTrainer(Long id) {
        Trainer trainer = userService.getTrainer(id);
        if (trainer == null){
            throw new TrainerNotFoundException();
        }
        return trainer;
    }

    @ExceptionHandler(TrainerNotFoundException.class)
    public String notFound(){
        return "weekplan/trainerNotFound";
    }

}
