package com.hsba.bi.fitnessstudio.Fitnessstudio.web.course;

import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Course;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping(path = "/showCourses")
    public String show(Model model) {
        model.addAttribute("courses", courseService.findAll());
        return "courses/showCourses";
    }

    @PostMapping(path = "/showCourses/add")
    public String addCourse(Course course) {
        courseService.save(course);
        return "redirect:/courses/showCourses";
    }

    @PostMapping(path = "/deleteCourse/{id}")
    public String deleteCourse(@PathVariable("id") Long id) {
        courseService.deleteById(id);
        return "redirect:/courses/showCourses";
    }

}
