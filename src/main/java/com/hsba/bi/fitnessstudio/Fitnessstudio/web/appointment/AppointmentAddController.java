package com.hsba.bi.fitnessstudio.Fitnessstudio.web.appointment;

import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Appointment;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Course;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Room;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service.AppointmentService;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service.DayOfWeekTranslator;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service.RoomService;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.Trainer;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.User;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.UserService;
import com.hsba.bi.fitnessstudio.Fitnessstudio.web.ForbiddenException;
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

/**
 * Controller zum Hinzufügen von Trainern
 */

@Controller
@RequiredArgsConstructor
//Controlelr wird erst aufgerufen, wenn der Trainer für den neuen Termin ausgewählt wurde
@RequestMapping(path = "weekplan/selectTrainer/{id}")
public class AppointmentAddController {

    private final AppointmentService appointmentService;
    private final RoomService roomService;
    private final UserService userService;
    private final AppointmentFormConverter formConverter;

    //Zunächst werden die Daten im Modell gefüllt
    //Zuärst wird anhand der ID im Pfad der Trainer geholt
    @ModelAttribute("trainer")
    public Trainer getTrainerForModel(@PathVariable("id") Long id){
        return userService.getTrainer(id);
    }
    @ModelAttribute("rooms")
    public List<Room> getRooms(){
        return roomService.findAll();
    }

    //Anschließend werden die Kurse geholt, die der Trainer anbietet
    @ModelAttribute("courses")
    public Set<Course> getCourses(@PathVariable("id") Long id){
        Trainer trainer = getTrainer(id);
        return trainer.getCourses();
    }

    //Anschließend werden die Arbeitstage geholt, die der Trainer anbietet
    @ModelAttribute("dayOfWeeks")
    public Set<DayOfWeek> getDayOfWeeks(@PathVariable("id") Long id){
        Trainer trainer = getTrainer(id);
        return trainer.getWorkingDays();
    }

    //Aufgrund von Thymeleaf-Problemen werden einem die Tage auf Deutsch übersetzt
    @ModelAttribute("daysOfWeekInGerman")
    public String[] getDaysOfWeekInGerman(){
        return DayOfWeekTranslator.dayOfWeekToGerman();
    }

    //Anzeigen des Termin-Formulares
    @GetMapping
    public String showAddAppointmentSite(Model model){
        model.addAttribute("appointmentForm", new AppointmentForm());
        return "weekplan/addAppointment";
    }

    @PostMapping
    public String addAppointment(@PathVariable("id") Long id, @ModelAttribute("appointmentForm") @Valid AppointmentForm appointmentForm, BindingResult appointmentBinding, Model model){
        //Bei Fehlern in der Datenvalidierung wird das Formular zurückgesendet
        if (appointmentBinding.hasErrors()){
            model.addAttribute("appointmentForm", appointmentForm);
            return "weekplan/addAppointment";
        }
        //Owner des Terminobjektes wird gesetzt
        User currentUser = userService.getUserByUsername(User.getCurrentUsername());
        Appointment appointment = new Appointment(currentUser);
        //Trainer wird gesetzt
        appointment.setTrainer(userService.getTrainer(id));
        //Form-Objekt wird in richtiges Termin-Objekt konvertiert
        formConverter.update(appointment, appointmentForm);
        //Falls Trainer nicht verfügbar ist, wird das Formular zurückgesendet mit Fehlermeldung
        if (appointmentService.checkIfTrainerIsAvailable(appointment) == false){
            model.addAttribute("appointmentForm", appointmentForm);
            model.addAttribute("trainerIsBooked", "Der Trainer ist zu diesem Zeitraum nicht verfügbar, bitte einen anderen Zeitraum auswählen");
            return "weekplan/addAppointment";
        }
        //Falls Raum nicht verfügbar ist, wird das Formular zurückgesendet mit Fehlermeldung
        if (appointmentService.checkIfRoomIsAvailable(appointment) == false){
            model.addAttribute("appointmentForm", appointmentForm);
            model.addAttribute("roomIsBooked", "Der Raum ist zu diesem Zeitraum nicht verfügbar, bitte einen anderen Zeitraum auswählen");
            return "weekplan/addAppointment";
        }
        //Falls der User kein Owner ist, wird Forbidden-Exception geworfen
        if (!appointment.isOwnedByCurrentUser()){
            throw new ForbiddenException();
        }
        //Sonst wird der Termin gespeichert
        appointmentService.save(appointment);
        return "redirect:/weekplan/showWeekplan";
    }

    private Trainer getTrainer(Long id) {
        Trainer trainer = userService.getTrainer(id);
        if (trainer == null){
            throw new TrainerNotFoundException();
        }
        return trainer;
    }

    //Falls Trainer nicht gefunden werden kann, wird TrainerNotFoundException geworfen
    @ExceptionHandler(TrainerNotFoundException.class)
    public String notFound(){
        return "weekplan/trainerNotFound";
    }

}
