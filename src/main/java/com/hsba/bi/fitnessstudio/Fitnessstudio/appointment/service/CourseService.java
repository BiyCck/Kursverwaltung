package com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service;

import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Course;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public List<Course> findAll(){
        return courseRepository.findAll();
    }

    public Course save(Course course){
        return courseRepository.save(course);
    }
}
