package com.hsba.bi.fitnessstudio.Fitnessstudio.web;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller für Beginn der Applikation + LogIn
 */

@Controller
@RequestMapping("/")
public class IndexController{

    //Weiterleitung auf die Startseite
    @GetMapping
    public String index() {
        return "redirect:/weekplan/";
    }

    //Authentifizierung für Login-Seite
    @GetMapping("/login")
    public String login() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //Falls der
        return auth instanceof AnonymousAuthenticationToken ? "login" : "redirect:/";
    }

}
