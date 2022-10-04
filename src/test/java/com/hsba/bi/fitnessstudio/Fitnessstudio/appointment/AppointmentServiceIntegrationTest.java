package com.hsba.bi.fitnessstudio.Fitnessstudio.appointment;

import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Appointment;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Course;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Room;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.repository.AppointmentRepository;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.repository.CourseRepository;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.repository.RoomRepository;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service.AppointmentService;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service.CourseService;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service.RoomService;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.Trainer;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.User;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.UserRepository;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.UserService;
import org.hibernate.service.spi.InjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@SpringBootTest
@Transactional
public class AppointmentServiceIntegrationTest {

    @Autowired
    private AppointmentService serviceToTest;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomRepository roomRepository;

    private final User admin = createUser("Test", "123456", "Test Cicek", User.ADMIN_ROLE);

    private final Course laufband = createCourse("Laufbänder", "Laufbandtraining für Anfänger", "Anfänger", "Cardio", admin);
    private final Course crossfit = createCourse("Crossfit", "Crossfit für Anfänger", "Anfänger", "Cardio", admin);

    private final Trainer trainer1 = createTrainer("Biyan123", "123456", "Biyan Cicek", Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY), Set.of(laufband, crossfit));
    private final Trainer trainer2 = createTrainer("Arian123", "123456", "Arian Bakhtiari", Set.of(DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY), Set.of(laufband, crossfit));
    private final Trainer trainer3 = createTrainer("Luis123", "123456", "Luis Carrero", Set.of(DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY), Set.of(laufband, crossfit));
    private final Trainer trainer4 = createTrainer("Mert123", "123456", "Mert Ulas", Set.of(DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY), Set.of(laufband, crossfit));

    Room room = new Room("Trainingsraum 1");

    @BeforeEach
    void setUp(){
        appointmentRepository.deleteAll();
        userService.save(admin);
        userService.save(trainer1);
        userService.save(trainer2);
        userService.save(trainer3);
        userService.save(trainer4);
        courseService.save(laufband);
        courseService.save(crossfit);
        roomService.save(room);
    }


    //Test zum Überprüfen, ob das Repository den richtigen Termin mit dem jeweiligen Trainer zurückgibt
    @Test
    void shouldFindAppointmentsByTrainer(){
        Appointment appointment1 = buildAppointmentWithTrainer(trainer1);
        Appointment appointment2 = buildAppointmentWithTrainer(trainer2);
        Appointment appointment3 = buildAppointmentWithTrainer(trainer3);


        //when
        List<Appointment> appointmentsInRepository = serviceToTest.findAll();

        //then
        assertThat(appointmentsInRepository).containsExactlyInAnyOrder(appointment1,appointment2,appointment3);

        //when
        appointmentsInRepository = serviceToTest.findByTrainer(trainer1);

        //then
        assertThat(appointmentsInRepository).containsExactly(appointment1);

        //when
        appointmentsInRepository = serviceToTest.findByTrainer(trainer2);

        //then
        assertThat(appointmentsInRepository).containsExactly(appointment2);

        //when
        appointmentsInRepository = serviceToTest.findByTrainer(trainer4);

        //then
        assertThat(appointmentsInRepository).isEmpty();


    }


    private User createUser(String username, String password, String name, String role){
        return new User(username, password, name, role);
    }

    private Trainer createTrainer(String username, String password, String name, Set<DayOfWeek> workingDays, Set<Course> courses){
        return new Trainer(username, password, name, workingDays, courses);
    }

    private Course createCourse(String name, String description, String targetGroup, String category, User user){
        Course course = new Course(user);
        course.setName(name);
        course.setDescription(description);
        course.setTargetGroup(targetGroup);
        course.setCategory(category);
        return course;
    }

    public Appointment buildAppointmentWithTrainer(Trainer trainer){
        Appointment appointment = new Appointment(admin);
        appointment.setDayOfWeek(DayOfWeek.MONDAY);
        appointment.setCourse(laufband);
        appointment.setTrainer(trainer);
        appointment.setRoom(room);
        appointment.setLocalTime(LocalTime.NOON);
        return serviceToTest.save(appointment);
    }
}
