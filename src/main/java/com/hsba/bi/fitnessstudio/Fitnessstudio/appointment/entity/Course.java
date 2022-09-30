package com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity;

import com.hsba.bi.fitnessstudio.Fitnessstudio.user.Trainer;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Course implements Comparable<Course>{

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private Long id;

    @Basic(optional = false)
    private String name;

    @Basic(optional = false)
    private String description;

    @Basic(optional = false)
    private String targetGroup;

    @Basic(optional = false)
    private String category;

    @ManyToOne(optional = false)
    @Setter(AccessLevel.NONE)
    private User owner;

    public Course(User owner){
        this.owner = owner;
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "course")
    private Set<Appointment> appointments;

    @ManyToMany(mappedBy = "courses")
    private Set<Trainer> trainers;

    public Course(String name, String description, String targetGroup, String category) {
        this.name = name;
        this.description = description;
        this.targetGroup = targetGroup;
        this.category = category;
    }

    @Override
    public int compareTo(Course other) {
        return this.name.compareTo(other.getName());
    }

    public boolean isOwnedByCurrentUser() {
        return this.owner != null && this.owner.getUsername().equals(User.getCurrentUsername());
    }

}
