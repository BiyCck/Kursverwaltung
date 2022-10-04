package com.hsba.bi.fitnessstudio.Fitnessstudio.user;

import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Course;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;



    //Test zum Überprüfen, ob das Repository den Trainer mit dem richten Nutzernamen zurückgibt
    @Test
    void shouldFindTrainerByUsername(){
        //given
        User admin = createUser("Test", "123456", "Test Cicek", User.ADMIN_ROLE);
        Course laufband = createCourse("Laufbänder", "Laufbandtraining für Anfänger", "Anfänger", "Cardio", admin);
        userRepository.save(admin);
        courseRepository.save(laufband);
        Trainer trainer2 = createTrainer("Arian789", "123456", "Arian Bakhtiari", Set.of(DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY), Set.of(laufband));
        userRepository.save(trainer2);
        //when
        Trainer trainer = userRepository.findTrainerByUsername("Arian789").orElse(null);
        //then
        assertThat(trainer).isEqualTo(trainer2);
    }

    //Test zum Überprüfen, ob das Repository alle Trainer zurückgibt
    @Test
    void shouldFindAllTrainer(){
        //given
        User admin = createUser("Test", "123456", "Test Cicek", User.ADMIN_ROLE);
        Course laufband = createCourse("Laufbänder", "Laufbandtraining für Anfänger", "Anfänger", "Cardio", admin);
        userRepository.save(admin);
        courseRepository.save(laufband);
        Trainer trainer1 = createTrainer("Biyan123", "123456", "Biyan Cicek", Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY), Set.of(laufband));
        Trainer trainer2 = createTrainer("Arian123", "123456", "Arian Bakhtiari", Set.of(DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY), Set.of(laufband));
        Trainer trainer3 = createTrainer("Luis123", "123456", "Luis Carrero", Set.of(DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY), Set.of(laufband));
        userRepository.save(trainer1);
        userRepository.save(trainer2);
        userRepository.save(trainer3);

        //when
        List<Trainer> trainers = userRepository.findAllTrainer();

        //then
        assertThat(trainers).contains(trainer1,trainer2,trainer3);

    }

    private Trainer createTrainer(String username, String password, String name, Set<DayOfWeek> workingDays, Set<Course> courses){
        return new Trainer(username, password, name, workingDays, courses);
    }

    private User createUser(String username, String password, String name, String role){
        return new User(username, password, name, role);
    }

    private Course createCourse(String name, String description, String targetGroup, String category, User user){
        Course course = new Course(user);
        course.setName(name);
        course.setDescription(description);
        course.setTargetGroup(targetGroup);
        course.setCategory(category);
        return course;
    }
}
