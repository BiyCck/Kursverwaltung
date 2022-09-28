package com.hsba.bi.fitnessstudio.Fitnessstudio.web.user;

import com.hsba.bi.fitnessstudio.Fitnessstudio.user.User;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.UserService;
import com.hsba.bi.fitnessstudio.Fitnessstudio.web.course.CourseForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class UserShowController {

    private final UserService userService;
    private final UserFormConverter formConverter;

    @ModelAttribute("userRoles")
    public String[] getUserRoles(){
        return User.getRoleConstants();
    }

    @GetMapping(path = "/showUsers")
    public String show(Model model){
        model.addAttribute("users", userService.findAll());
        return "user/showUsers";
    }

    @GetMapping(path = "/addUser")
    public String showAddUser(@PathVariable Long id, Model model) {
        UserForm userForm = formConverter.toForm(new User());
        model.addAttribute("userForm", userForm);
        return "user/addUser";
    }

    @PostMapping(path = "/addUser")
    public String addUser(@PathVariable Long id, @ModelAttribute @Valid UserForm userForm, BindingResult userBinding, Model model) {
        if (userBinding.hasErrors()){
            model.addAttribute("userForm", userForm);
            return "user/addUser";
        }
        userService.save(formConverter.update(new User(), userForm));
        return "redirect:/user/showUsers";
    }


    @GetMapping(path = "/editUser/{id}")
    public String showEditUser(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        UserForm userForm = formConverter.toForm(userService.getUser(id));
        model.addAttribute("userForm", userForm);
        return "user/editUser";
    }

    @PostMapping(path = "/editCourse/{id}")
    public String editUser(@PathVariable Long id, @ModelAttribute @Valid UserForm userForm, BindingResult userBinding, Model model) {
        if (userBinding.hasErrors()){
            model.addAttribute("userForm", userForm);
            return "user/editUser";
        }
        userService.save(formConverter.update(userService.getUser(id), userForm));
        return "redirect:/user/showUsers";
    }

    @PostMapping(path = "/deleteCourse/{id}")
    public String deleteCourse(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/user/showUsers";
    }
}
