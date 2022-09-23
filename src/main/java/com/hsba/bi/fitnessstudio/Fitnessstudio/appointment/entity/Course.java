package com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity;

import com.hsba.bi.fitnessstudio.Fitnessstudio.user.Trainer;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * @abakhtiari Klasse für Kurs Objekt
 */

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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "course")
    private Set<Appointment> appointments;

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
}
