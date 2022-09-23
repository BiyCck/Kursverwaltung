package com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service;

import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Appointment;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @bcicek Service-Klasse für Wochenplan-Objekt
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

}
