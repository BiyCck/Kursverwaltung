package com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity;

import com.hsba.bi.fitnessstudio.Fitnessstudio.user.Trainer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @bcicek Klasse für Termin-Objekt im Wochenplan
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Appointment {


    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private Long id;

    @Basic(optional = false)
    DayOfWeek dayOfWeek;

    @Basic(optional = false)
    LocalTime localTime;

    @ManyToOne(optional = false)
    private Room room;

    @ManyToOne(optional = false)
    private Trainer trainer;

    @ManyToOne(optional = false)
    private Course course;

    public Appointment(DayOfWeek dayOfWeek, LocalTime localTime, Room room, Trainer trainer, Course course) {
        this.dayOfWeek = dayOfWeek;
        this.localTime = localTime;
        this.room = room;
        this.trainer = trainer;
        this.course = course;
    }
}
