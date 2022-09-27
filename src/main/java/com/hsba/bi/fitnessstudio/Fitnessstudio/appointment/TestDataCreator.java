package com.hsba.bi.fitnessstudio.Fitnessstudio.appointment;

import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Course;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Room;
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
import java.util.Set;

@Component
@RequiredArgsConstructor
@Transactional
public class TestDataCreator {

    private final UserService userService;
    private final RoomService roomService;
    private final CourseService courseService;
    private final PasswordEncoder passwordEncoder;

    @EventListener(ApplicationStartedEvent.class)
    public void init(){

        //Kurse
        Course cardio = courseService.save(new Course("Laufbänder", "Laufbandtraining für Anfänger", "Anfänger", "Cardio"));
        Course course = courseService.save(new Course("Crossfit", "Crossfit für Anfänger", "Anfänger", "Cardio"));

        //User & Trainer
        createUser("Biyan456", "123456", "Biyan Cicek", User.USER_ROLE);
        createUser("Test", "123456", "Test Cicek", User.ADMIN_ROLE);
        createTrainer("Biyan123", "123456", "Biyan Cicek", Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY), Set.of(cardio, course));

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
    }

    private User createUser(String username, String password, String name, String role){
        return userService.save(new User(username, passwordEncoder.encode(password), name, role));
    }

    private Trainer createTrainer(String username, String password, String name, Set<DayOfWeek> workingDays, Set<Course> courses){
        return userService.save(new Trainer(username, passwordEncoder.encode(password), name, workingDays, courses));
    }
}
