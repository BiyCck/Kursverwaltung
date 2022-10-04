package com.hsba.bi.fitnessstudio.Fitnessstudio.web.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * Formularobjekt für User-Objekte
 */

@Getter
@Setter
public class UserForm {

    @NotBlank(message = "Bitte einen Usernamen vergeben")
    private String username;

    @NotBlank(message = "Bitte einen Password vergeben")
    private String password;

    @NotBlank(message = "Bitte einen Namen vergeben")
    private String name;

    @NotBlank(message = "Bitte eine Rolle vergeben")
    private String role;

}
