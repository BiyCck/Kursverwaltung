package com.hsba.bi.fitnessstudio.Fitnessstudio.web.course;

import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Course;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service.CourseService;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.User;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.UserService;
import com.hsba.bi.fitnessstudio.Fitnessstudio.web.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller für alle Operationen mit Kursobjekten
 */

@Controller
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final CourseFormConverter formConverter;
    private final UserService userService;



    //Anzeigen der Kursseite
    @GetMapping(path = "/showCourses")
    public String show(Model model) {
        model.addAttribute("courses", courseService.findAll());
        return "courses/showCourses";
    }

    //Anzeigen des Formulares beim Hinzufügen eines Kurses
    @GetMapping(path = "/showCourses/add")
    public String showAddCourse(Model model) {
        CourseForm courseForm = formConverter.toForm(new Course());
        model.addAttribute("courseForm", courseForm);
        return "courses/addCourse";
    }

    //Hinzufügen eines neuen Kurses
    @PostMapping(path = "/showCourses/add")
    public String addCourse(@ModelAttribute @Valid CourseForm courseForm, BindingResult courseBinding, Model model) {
        //Formular wird validiert, Formular wird bei Fehlern zurückgesendet
        if (courseBinding.hasErrors()){
            model.addAttribute("courseForm", courseForm);
            return "courses/addCourse";
        }
        //Jetziger User wird abgegriffen und als Owner gesetzt
        User currentUser = userService.getUserByUsername(User.getCurrentUsername());
        //Neues Kursobjekt wird mit den Daten des Formulares gesetzt
        Course course = formConverter.update(new Course(currentUser), courseForm);
        //Kurs wird gespeichert
        courseService.save(course);
        return "redirect:/courses/showCourses";
    }

    //Formular zum Bearbeiten von Kursen wird angezeigt
    @GetMapping(path = "/editCourse/{id}")
    public String showEditCourse(@PathVariable Long id, Model model) {
        model.addAttribute("course", courseService.getCourse(id));
        CourseForm courseForm = formConverter.toForm(courseService.getCourse(id));
        model.addAttribute("courseForm", courseForm);
        return "courses/editCourse";
    }

    //Bearbeiten eines Kurses
    @PostMapping(path = "/editCourse/{id}")
    public String editCourse(@PathVariable Long id, @ModelAttribute @Valid CourseForm courseForm, BindingResult courseBinding, Model model) {
        //Formular wird validiert, Formular wird bei Fehlern zurückgesendet
        if (courseBinding.hasErrors()){
            model.addAttribute("courseForm", courseForm);
            return "courses/editCourse";
        }
        //Neues Kursobjekt wird mit den Daten des Formulares gesetzt
        Course course = formConverter.update(courseService.getCourse(id), courseForm);
        //Wenn der User nicht der Owner ist, soll ForbiddenException geworfen werden
        if (!course.isOwnedByCurrentUser()){
            throw new ForbiddenException();
        }
        //Bearbeitung wird gespeichert
        courseService.save(course);
        return "redirect:/courses/showCourses";
    }

    //Löschen von Kursen
    @PostMapping(path = "/deleteCourse/{id}")
    public String deleteCourse(@PathVariable("id") Long id) {
        courseService.deleteById(id);
        return "redirect:/courses/showCourses";
    }

}
