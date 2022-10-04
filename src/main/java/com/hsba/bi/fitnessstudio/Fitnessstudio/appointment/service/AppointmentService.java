package com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service;

import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Appointment;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Room;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.repository.AppointmentRepository;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.Trainer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Service-Klasse für Termin-Objekt
 */

@Service
@Transactional
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    public List<Appointment> findAll(){
        return appointmentRepository.findAll();
    }

    public Appointment save(Appointment appointment){
        return appointmentRepository.save(appointment);
    }

    public void delete(Long id){
        appointmentRepository.deleteById(id);
    }

    public Appointment getAppointment(Long id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    //Überpüft ob der Trainer verfügbar ist für neuen Termin
    public boolean checkIfTrainerIsAvailable(Appointment appointment){
        Trainer trainer = appointment.getTrainer();
        if (!trainer.getAppointments().isEmpty()){
            //Es wird jeder Termin geprüft vom Trainer
            for (Appointment trainer_appointment : trainer.getAppointments()){
                //Falls es ein Termin gibt, der den selben Wochentag und die selbe Uhrzeit hat, soll False zurückgegeben werden
                if (trainer_appointment.getDayOfWeek().equals(appointment.getDayOfWeek()) && trainer_appointment.getLocalTime().equals(appointment.getLocalTime())){
                    //Falls es sich um den selben Termin handelt, der schon bei der Liste vom Trainer ist, soll er geskipped werden
                    if (appointment.equals(trainer_appointment)){
                        continue;
                    } else {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public List<Appointment> findByTrainer(Trainer trainer){
        return appointmentRepository.findAppointmentByTrainer(trainer);
    }

    //Überpüft ob der Raum verfügbar ist für neuen Termin
    public boolean checkIfRoomIsAvailable(Appointment appointment){

        Room room = appointment.getRoom();
        if (!room.getAppointment().isEmpty()) {
            //Es wird jeder Termin geprüft vom Raum
            for (Appointment room_appointment : room.getAppointment()) {
                //Falls es ein Termin gibt, der den selben Wochentag und die selbe Uhrzeit hat, soll False zurückgegeben werden
                if (room_appointment.getDayOfWeek().equals(appointment.getDayOfWeek()) && room_appointment.getLocalTime().equals(appointment.getLocalTime())) {
                    //Falls es sich um den selben Termin handelt, der schon bei der Liste vom Raum ist, soll er geskipped werden
                    if (appointment.equals(room_appointment)){
                        continue;
                    } else {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
