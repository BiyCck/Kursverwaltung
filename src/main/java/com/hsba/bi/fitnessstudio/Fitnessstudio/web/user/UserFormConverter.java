package com.hsba.bi.fitnessstudio.Fitnessstudio.web.user;

import com.hsba.bi.fitnessstudio.Fitnessstudio.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserFormConverter {

    UserForm toForm(User user){
        UserForm userForm = new UserForm();
        userForm.setUsername(user.getUsername());
        userForm.setPassword(user.getPassword());
        userForm.setName(user.getName());
        userForm.setRole(user.getRole());
        return userForm;
    }

    User update(User user, UserForm userForm){
        user.setUsername(userForm.getUsername());
        user.setPassword(userForm.getPassword());
        user.setName(userForm.getName());
        user.setRole(userForm.getRole());
        return user;
    }
}
