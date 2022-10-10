package com.hsba.bi.fitnessstudio.Fitnessstudio.web.appointment;

import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Appointment;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Course;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Room;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service.AppointmentService;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service.DayOfWeekTranslator;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service.RoomService;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.Trainer;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.UserService;
import com.hsba.bi.fitnessstudio.Fitnessstudio.web.ForbiddenException;
import com.hsba.bi.fitnessstudio.Fitnessstudio.web.TrainerNotFoundException;
import com.hsba.bi.fitnessstudio.Fitnessstudio.web.AppointmentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

/**
 * Controller zum Bearbeiten von Terminobjekten
 */

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/weekplan/editAppointment/{id}")
@PreAuthorize("hasRole('ADMIN')")
public class AppointmentEditController {

    private final AppointmentService appointmentService;
    private final RoomService roomService;
    private final UserService userService;
    private final AppointmentFormConverter formConverter;

    //Laden der Modelattribute
    //Greift vom Pfad die ID des Termins ab, holt den Termin
    @ModelAttribute("appointment")
    public Appointment getAppointmentForModel(@PathVariable("id") Long id){
        return getAppointment(id);
    }

    //Greift den Trainer vom jeweiligen Termin ab
    @ModelAttribute("trainer")
    public Trainer getTrainerForModel(@PathVariable("id") Long id){
        return getTrainer(id);
    }

    @ModelAttribute("rooms")
    public List<Room> getRooms(){
        return roomService.findAll();
    }

    //Greift den Kurs vom jeweiligen Trainer ab
    @ModelAttribute("courses")
    public Set<Course> getCourses(@PathVariable("id") Long id){
        Trainer trainer = getTrainer(id);
        return trainer.getCourses();
    }

    //Greift die Arbeitstage vom jeweiligen Trainer ab
    @ModelAttribute("dayOfWeeks")
    public Set<DayOfWeek> getDayOfWeeks(@PathVariable("id") Long id){
        Trainer trainer = getTrainer(id);
        return trainer.getWorkingDays();
    }

    @ModelAttribute("daysOfWeekInGerman")
    public String[] getDaysOfWeekInGerman(){
        return DayOfWeekTranslator.dayOfWeekToGerman();
    }

    //Anzeigen der Edit-Appointment-Seite
    @GetMapping
    public String showEditAppointmentSite(@PathVariable("id") Long id, Model model){
        Appointment appointment = getAppointment(id);
        if (!appointment.isOwnedByCurrentUser()){
            throw new ForbiddenException();
        }
        //Wandelt den ausgewählten Termin als Formular-Objekt um
        model.addAttribute("appointmentForm", formConverter.toForm(appointment));
        return "weekplan/editAppointment";
    }

    @PostMapping
    public String editAppointment(@PathVariable("id") Long id, @ModelAttribute("appointmentForm") @Valid AppointmentForm appointmentForm, BindingResult appointmentBinding, Model model){
        //Bei Fehlern in der Validierung wird die Seite zurückgegeben
        if (appointmentBinding.hasErrors()){
            model.addAttribute("appointmentForm", appointmentForm);
            return "weekplan/editAppointment";
        }
        Appointment appointment = formConverter.update(getAppointment(id), appointmentForm);
        if (!appointment.isOwnedByCurrentUser()){
            throw new ForbiddenException();
        }
        if (appointmentService.checkIfTrainerIsAvailable(appointment) == false){
            model.addAttribute("appointmentForm", appointmentForm);
            model.addAttribute("trainerIsBooked", "Der Trainer ist zu diesem Zeitraum nicht verfügbar, bitte einen anderen Zeitraum auswählen");
            return "weekplan/editAppointment";
        }
        //Falls Raum nicht verfügbar ist, wird das Formular zurückgesendet mit Fehlermeldung
        if (appointmentService.checkIfRoomIsAvailable(appointment) == false){
            model.addAttribute("appointmentForm", appointmentForm);
            model.addAttribute("roomIsBooked", "Der Raum ist zu diesem Zeitraum nicht verfügbar, bitte einen anderen Zeitraum auswählen");
            return "weekplan/editAppointment";
        }
        appointmentService.save(appointment);
        return "redirect:/weekplan/showWeekplan";
    }


    private Trainer getTrainer(@PathVariable("id") Long id) {
        Appointment appointment = appointmentService.getAppointment(id);
        Trainer trainer = appointment.getTrainer();
        if (trainer == null){
            throw new TrainerNotFoundException();
        }
        return trainer;
    }

    private Appointment getAppointment(@PathVariable("id") Long id){
        Appointment appointment = appointmentService.getAppointment(id);
        if (appointment == null){
            throw new AppointmentNotFoundException();
        }
        return appointment;
    }

    @ExceptionHandler(TrainerNotFoundException.class)
    public String trainerNotFound(){
        return "weekplan/trainerNotFound";
    }

    @ExceptionHandler(AppointmentNotFoundException.class)
    public String appointmentNotFound(){
        return "weekplan/appointmentNotFound";
    }

}
