package com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity;

import com.hsba.bi.fitnessstudio.Fitnessstudio.user.Trainer;
import com.hsba.bi.fitnessstudio.Fitnessstudio.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
    private DayOfWeek dayOfWeek;

    @Basic(optional = false)
    private LocalTime localTime;

    @ManyToOne(optional = false)
    private Room room;

    @ManyToOne(optional = false)
    private Trainer trainer;

    @ManyToOne(optional = false)
    private Course course;

    @ManyToOne(optional = false)
    @Setter(AccessLevel.NONE)
    private User owner;

    public Appointment(User owner){
        this.owner = owner;
    }

    public Appointment(DayOfWeek dayOfWeek, LocalTime localTime, Room room, Trainer trainer, Course course) {
        this.dayOfWeek = dayOfWeek;
        this.localTime = localTime;
        this.room = room;
        this.trainer = trainer;
        this.course = course;
    }

    public boolean checkIfTrainerDataIsValid(){
        return this.trainer.getCourses().contains(this.course) && this.trainer.getWorkingDays().contains(this.dayOfWeek);
    }

    public boolean isOwnedByCurrentUser() {
        return this.owner != null && this.owner.getUsername().equals(User.getCurrentUsername());
    }

}
