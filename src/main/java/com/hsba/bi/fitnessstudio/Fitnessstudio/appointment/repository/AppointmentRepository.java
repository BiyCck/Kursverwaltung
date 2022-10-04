package com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.repository;

import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Appointment;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.Trainer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Repository-Klasse für Termin-Objekt
 */

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    //Methode zum Finden von Terminen basierend auf Rolle
    List<Appointment> findAppointmentByTrainer(Trainer trainer);
}
