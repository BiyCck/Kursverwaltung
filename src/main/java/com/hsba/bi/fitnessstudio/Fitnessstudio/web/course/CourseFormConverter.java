package com.hsba.bi.fitnessstudio.Fitnessstudio.web.course;

import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Course;
import com.hsba.bi.fitnessstudio.Fitnessstudio.web.course.CourseForm;
import org.springframework.stereotype.Component;

@Component
public class CourseFormConverter {

    CourseForm toForm(Course course){
        CourseForm form = new CourseForm();
        form.setName(course.getName());
        form.setDescription(course.getDescription());
        form.setTargetGroup(course.getTargetGroup());
        form.setCategory(course.getCategory());
        return form;
    }

    Course update(Course course, CourseForm form){
        course.setName(form.getName());
        course.setDescription(form.getDescription());
        course.setTargetGroup(form.getTargetGroup());
        course.setCategory(form.getCategory());
        return course;
    }
}
