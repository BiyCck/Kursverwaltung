package com.hsba.bi.fitnessstudio.Fitnessstudio.web.user;

import com.hsba.bi.fitnessstudio.Fitnessstudio.user.Trainer;
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
    public String showAddUser(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return "user/addUser";
    }

    @PostMapping(path = "/addUser")
    public String addUser(@ModelAttribute @Valid UserForm userForm, BindingResult userBinding, Model model) {
        if (userBinding.hasErrors()){
            model.addAttribute("userForm", userForm);
            return "user/addUser";
        }
        if (userForm.getRole().equals(User.TRAINER_ROLE)){
            userService.save(formConverter.updateToTrainer(new Trainer(), userForm));
        } else {
            userService.save(formConverter.update(new User(), userForm));
        }
        return "redirect:/user/showUsers";
    }

    @PostMapping(path = "/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/user/showUsers";
    }
}
