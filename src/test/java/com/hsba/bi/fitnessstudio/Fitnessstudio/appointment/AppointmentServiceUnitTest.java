package com.hsba.bi.fitnessstudio.Fitnessstudio.appointment;

import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Appointment;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Course;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Room;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.repository.AppointmentRepository;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service.AppointmentService;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.Trainer;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@MockitoSettings
public class AppointmentServiceUnitTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentService serviceToTest;

    //Test zum Überprüfen, ob der Service die richtige Methode vom Repository aufruft und die richtigen Termine zurückgibt
    @Test
    void shoudReturnAllAppointmenstIfTrainerIsNotSelected(){
        //given
        Appointment appointment1 = new Appointment(DayOfWeek.FRIDAY, LocalTime.NOON, new Room(), new Trainer(), new Course());
        Appointment appointment2 = new Appointment(DayOfWeek.FRIDAY, LocalTime.NOON, new Room(), new Trainer(), new Course());
        Mockito.when(appointmentRepository.findAll()).thenReturn(List.of(appointment1, appointment2));
        //when
        final List<Appointment> appointments = serviceToTest.findAll();
        //then
        assertThat(appointments).hasSize(2).contains(appointment1, appointment2);
        verify(appointmentRepository, never()).findAppointmentByTrainer(new Trainer());

    }

    //Test zum Überprüfen, ob der Service die richtige Methode aufruft und die richtigen Termine mit dem jeweiligen Trainer zurückgibt
    @Test
    void shouldReturnAppointmentsIfTrainerIsSelected(){
        //given
        Trainer trainer1 = createTrainer("Biyan123", "123456", "Biyan Cicek", Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY), Set.of(new Course()));
        Appointment appointment1 = new Appointment(DayOfWeek.FRIDAY, LocalTime.NOON, new Room(), trainer1, new Course());
        Appointment appointment2 = new Appointment(DayOfWeek.FRIDAY, LocalTime.NOON, new Room(), new Trainer(), new Course());
        Mockito.when(appointmentRepository.findAppointmentByTrainer(trainer1)).thenReturn(List.of(appointment1));

        //when
        final List<Appointment> appointments = serviceToTest.findByTrainer(trainer1);
        //then
        assertThat(appointments).hasSize(1).contains(appointment1);
        verify(appointmentRepository, never()).findAll();
    }

    private Trainer createTrainer(String username, String password, String name, Set<DayOfWeek> workingDays, Set<Course> courses){
        return new Trainer(username, password, name, workingDays, courses);
    }

}
