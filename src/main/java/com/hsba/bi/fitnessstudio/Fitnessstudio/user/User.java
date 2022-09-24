package com.hsba.bi.fitnessstudio.Fitnessstudio.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "discrim")
@Table(name = "USERS")
@DiscriminatorValue("User")
public class User implements Comparable<User>{

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
