package com.hsba.bi.fitnessstudio.Fitnessstudio.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "discrim")
@Table(name = "USERS")
@DiscriminatorValue("User")
public class User implements Comparable<User>{

    public static String USER_ROLE = "USER";
    public static String TRAINER_ROLE = "TRAINER";
    public static String ADMIN_ROLE = "ADMIN";

    public static String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return null;
    }

    public static String[] getRoleConstants(){
        return new String[]{USER_ROLE, TRAINER_ROLE, ADMIN_ROLE};
    }

    @Id
    @GeneratedValue
    private Long id;

    @Basic(optional = false)
    private String username;

    @Basic(optional = false)
    private String password;

    @Basic(optional = false)
    private String name;

    @Basic(optional = false)
    private String role;

    public User(String username, String password, String name, String role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    @Override
    public int compareTo(User other) {
        return this.name.compareTo(other.name);
    }
}
