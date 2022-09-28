package com.hsba.bi.fitnessstudio.Fitnessstudio.web.course;

import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Course;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final CourseFormConverter formConverter;



    @GetMapping(path = "/showCourses")
    public String show(Model model) {
        model.addAttribute("courses", courseService.findAll());
        return "courses/showCourses";
    }

    @GetMapping(path = "/showCourses/add")
    public String showAddCourse(Model model) {
        CourseForm courseForm = formConverter.toForm(new Course());
        model.addAttribute("courseForm", courseForm);
        return "courses/addCourse";
    }

    @PostMapping(path = "/showCourses/add")
    public String addCourse(@ModelAttribute @Valid CourseForm courseForm, BindingResult courseBinding, Model model) {
        if (courseBinding.hasErrors()){
            model.addAttribute("courseForm", courseForm);
            return "courses/addCourse";
        }
        Course course = formConverter.update(new Course(), courseForm);
        courseService.save(course);
        return "redirect:/courses/showCourses";
    }

    @GetMapping(path = "/editCourse/{id}")
    public String showEditCourse(@PathVariable Long id, Model model) {
        model.addAttribute("course", courseService.getCourse(id));
        CourseForm courseForm = formConverter.toForm(courseService.getCourse(id));
        model.addAttribute("courseForm", courseForm);
        return "courses/editCourse";
    }

    @PostMapping(path = "/editCourse/{id}")
    public String editourse(@PathVariable Long id, @ModelAttribute @Valid CourseForm courseForm, BindingResult courseBinding, Model model) {
        if (courseBinding.hasErrors()){
            model.addAttribute("courseForm", courseForm);
            return "courses/editCourse";
        }
        courseService.save(formConverter.update(courseService.getCourse(id), courseForm));
        return "redirect:/courses/showCourses";
    }

    @PostMapping(path = "/deleteCourse/{id}")
    public String deleteCourse(@PathVariable("id") Long id) {
        courseService.deleteById(id);
        return "redirect:/courses/showCourses";
    }

}
