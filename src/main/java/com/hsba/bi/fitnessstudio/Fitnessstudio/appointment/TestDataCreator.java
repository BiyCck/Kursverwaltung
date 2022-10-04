package com.hsba.bi.fitnessstudio.Fitnessstudio.appointment;

import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Appointment;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Course;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Room;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service.AppointmentService;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service.CourseService;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service.RoomService;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.Trainer;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.User;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

/**
 * Klasse zum Kreiieren von Daten vor Start der Applikation
 */

@Component
@RequiredArgsConstructor
@Transactional
public class TestDataCreator {

    private final UserService userService;
    private final RoomService roomService;
    private final CourseService courseService;
    private final PasswordEncoder passwordEncoder;
    private final AppointmentService appointmentService;

    @EventListener(ApplicationStartedEvent.class)
    public void init(){

        //Admin hinzufügen
        User admin = createUser("Test", "123456", "Test Cicek", User.ADMIN_ROLE);

        //Kurse vorab hinzufügen
        Course laufband = createCourse("Laufbänder", "Laufbandtraining für Anfänger", "Anfänger", "Cardio");
        Course crossfit = createCourse("Crossfit", "Crossfit für Anfänger", "Anfänger", "Cardio");
        Course gewichteheben = createCourse("Gewichteheben", "Gewichteheben für Fortgeschrittene", "Fortgeschritten", "Kraft");
        Course fußball = createCourse("Fußball", "Fußball für Anfänger", "Anfänger", "Ballsport");

        //Trainer vorab hinzufügen
        Trainer trainer1 = createTrainer("Biyan123", "123456", "Biyan Cicek", Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY), Set.of(laufband, crossfit));
        Trainer trainer2 = createTrainer("Arian123", "123456", "Arian Bakhtiari", Set.of(DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY), Set.of(gewichteheben, fußball));
        Trainer trainer3 = createTrainer("Luis123", "123456", "Luis Carrero", Set.of(DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY), Set.of(crossfit, fußball));

        //Räume
        Room room1 = roomService.save(new Room("Trainingsraum 1"));
        Room room2 = roomService.save(new Room("Trainingsraum 2"));
        Room room3 = roomService.save(new Room("Trainingsraum 3"));
        Room room4 = roomService.save(new Room("Trainingsraum 4"));
        Room room5 = roomService.save(new Room("Trainingsraum 5"));
        Room room6 = roomService.save(new Room("Trainingsraum 6"));
        Room room7 = roomService.save(new Room("Trainingsraum 7"));
        Room room8 = roomService.save(new Room("Trainingsraum 8"));
        Room room9 = roomService.save(new Room("Trainingsraum 9"));
        Room room10 = roomService.save(new Room("Trainingsraum 10"));

        //Wochentermine hinzufügen
        createAppointment(DayOfWeek.MONDAY, LocalTime.NOON, room1, trainer1, crossfit, admin);
        createAppointment(DayOfWeek.FRIDAY, LocalTime.NOON, room3, trainer2, gewichteheben, admin);
        createAppointment(DayOfWeek.WEDNESDAY, LocalTime.NOON, room5, trainer3, fußball, admin);
    }

    private User createUser(String username, String password, String name, String role){
        return userService.save(new User(username, passwordEncoder.encode(password), name, role));
    }

    private Trainer createTrainer(String username, String password, String name, Set<DayOfWeek> workingDays, Set<Course> courses){
        return userService.save(new Trainer(username, passwordEncoder.encode(password), name, workingDays, courses));
    }

    private Course createCourse(String name, String description, String targetGroup, String category){
        Course course = new Course(userService.getUserByUsername("Test"));
        course.setName(name);
        course.setDescription(description);
        course.setTargetGroup(targetGroup);
        course.setCategory(category);
        return courseService.save(course);
    }
    private Appointment createAppointment(DayOfWeek dayOfWeek, LocalTime localTime, Room room, Trainer trainer, Course course, User owner){
        Appointment appointment = new Appointment(owner);
        appointment.setDayOfWeek(dayOfWeek);
        appointment.setLocalTime(localTime);
        appointment.setRoom(room);
        appointment.setTrainer(trainer);
        appointment.setCourse(course);
        return appointmentService.save(appointment);
    }
}
