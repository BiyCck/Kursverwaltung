package com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service;

import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.RoomIsBookedException;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.TrainerIsBookedException;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Appointment;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Room;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.repository.AppointmentRepository;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.repository.RoomRepository;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.Trainer;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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

    public boolean checkIfTrainerIsAvailable(Appointment appointment){

        Trainer trainer = appointment.getTrainer();
        for (Appointment trainer_appointment : trainer.getAppointments()){
            if (trainer_appointment.getDayOfWeek().equals(appointment.getDayOfWeek()) && trainer_appointment.getLocalTime().equals(appointment.getLocalTime())){
                return false;
            }
        }
        throw new TrainerIsBookedException();
    }

    public boolean checkIfRoomIsAvailable(Appointment appointment){

        Room room = appointment.getRoom();
        for (Appointment room_appointment : room.getAppointment()){
            if (room_appointment.getDayOfWeek().equals(appointment.getDayOfWeek()) && room_appointment.getLocalTime().equals(appointment.getLocalTime())){
                return true;
            }
        }
        throw new RoomIsBookedException();
    }

}
