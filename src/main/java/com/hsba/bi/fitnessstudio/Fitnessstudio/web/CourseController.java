package com.hsba.bi.fitnessstudio.Fitnessstudio.web;

import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Course;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/weekplan/showCourses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    //Anzeigen von Kursen
    @GetMapping
    public String show(Model model) {
        model.addAttribute("courses", courseService.findAll());
        return "weekplan/showCourses";
    }

    //Hinzufügen von Kursen
    @PostMapping(path = "/add")
    public String addCourse(Course course) {
        courseService.save(course);
        return "redirect:/weekplan/showCourses";
    }

}
