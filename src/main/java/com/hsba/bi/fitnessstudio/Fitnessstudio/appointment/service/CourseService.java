package com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service;

import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Course;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.repository.CourseRepository;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.Trainer;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.UserRepository;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public List<Course> findAll(){
        return courseRepository.findAll();
    }

    public Course save(Course course){
        return courseRepository.save(course);
    }

    public void deleteById(Long id){
        Course course = courseRepository.findById(id).orElse(null);
        List<Trainer> trainers = userRepository.findTrainersByCourse(course.getId());
        if (trainers.isEmpty()){
            System.out.println("Empty List");
        } else {
            for (Trainer trainer : trainers){
                trainer.getCourses().remove(course);
                userRepository.save(trainer);
            }
        }
        courseRepository.deleteById(id);
    }

    public Course getCourse(Long id){
        return courseRepository.findById(id).orElse(null);
    }
}
