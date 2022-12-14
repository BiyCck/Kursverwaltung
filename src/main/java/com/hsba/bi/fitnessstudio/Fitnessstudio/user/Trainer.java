package com.hsba.bi.fitnessstudio.Fitnessstudio.user;

import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Appointment;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Course;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.engine.internal.Cascade;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
//Discrim-Wert in der User-Tabelle soll Trainer sein
@DiscriminatorValue("Trainer")
//Join-Column soll die Spalte ID sein
@PrimaryKeyJoinColumn(name = "id")
public class Trainer extends User{

    public Trainer(String username, String password, String name, Set<DayOfWeek> workingDays, Set<Course> courses){
        super(username, password, name, TRAINER_ROLE);
        this.workingDays = workingDays;
        this.courses = courses;
    }

    @Basic(optional = false)
    private String role = TRAINER_ROLE;

    //Tabelle für die ManyToMany-Beziehung zwischen Trainer und Kurse soll erstellt werden, durch die IDs beider Objekte soll der Join erfolgen
    @ManyToMany
    @JoinTable(
            name = "trainer_courses",
            joinColumns = @JoinColumn(name = "trainer_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses;

    //Trainer ist Owner der Beziehung, deshalb soll über den Trainer gemapped werden
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "trainer")
    private Set<Appointment> appointments;

    //
    @ElementCollection(targetClass = DayOfWeek.class)
    @JoinTable(name = "workingDays", joinColumns = @JoinColumn(name = "trainer_id"))
    @Column(name = "workingDays", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> workingDays;

    public String getCourseNamesAsString() {
        return getCourses().stream().sorted().map(Course::getName).collect(Collectors.joining(", "));
    }

    public String getWorkingDaysAsString() {
        String workingDaysInGerman = "";
        int counter = 0;
        for (DayOfWeek workingDay : this.getWorkingDays().stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList())){
            if (counter == getWorkingDays().size() - 1){
                workingDaysInGerman += workingDay.getDisplayName(TextStyle.FULL, Locale.GERMAN);
            } else {
                workingDaysInGerman += workingDay.getDisplayName(TextStyle.FULL, Locale.GERMAN) + ", ";
            }
            counter++;
        }
        return workingDaysInGerman;
    }
}
