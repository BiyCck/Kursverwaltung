package com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.repository;

import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
