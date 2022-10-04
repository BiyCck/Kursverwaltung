package com.hsba.bi.fitnessstudio.Fitnessstudio.web.user;

import com.hsba.bi.fitnessstudio.Fitnessstudio.user.Trainer;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.User;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.UserService;
import com.hsba.bi.fitnessstudio.Fitnessstudio.web.course.CourseForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller für die Nutzerverwaltung
 */

@Controller
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class UserShowController {

    private final UserService userService;
    private final UserFormConverter formConverter;
    //Passwort soll nicht als Klartext abgespeichert werden, deshalb wird das Passwort vercrypted
    private final PasswordEncoder passwordEncoder;

    //Abholen der vorhandenen Nutzerrollen für das Erstellen von Nutzern
    @ModelAttribute("userRoles")
    public String[] getUserRoles(){
        return User.getRoleConstants();
    }

    //Anzeigen von allen Usern
    @GetMapping(path = "/showUsers")
    public String show(Model model){
        model.addAttribute("users", userService.findAll());
        return "user/showUsers";
    }

    //Anzeigen des Formulares für das Hinzufügen von Usern
    @GetMapping(path = "/addUser")
    public String showAddUser(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return "user/addUser";
    }

    //Hinzufügen von Usern
    @PostMapping(path = "/addUser")
    public String addUser(@ModelAttribute @Valid UserForm userForm, BindingResult userBinding, Model model) {
        //Falls Formular Fehler enthält, wird das Formular zurückgesendet
        if (userBinding.hasErrors()){
            model.addAttribute("userForm", userForm);
            return "user/addUser";
        }
        //Falls der neue Nutzer ein Trainer sein soll, wird das Formular als Trainer-Objekt konvertiert
        if (userForm.getRole().equals(User.TRAINER_ROLE)){
            User user = formConverter.update(new Trainer(), userForm);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.save(user);
        }
        //Sonst wird er als normaler Nutzer abgespeichert
        else {
            userService.save(formConverter.update(new User(), userForm));
        }
        return "redirect:/user/showUsers";
    }

    //Löschen von Nutzern
    @PostMapping(path = "/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/user/showUsers";
    }
}
