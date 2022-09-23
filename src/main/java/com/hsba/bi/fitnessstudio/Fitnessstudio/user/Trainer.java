package com.hsba.bi.fitnessstudio.Fitnessstudio.user;

import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Appointment;
import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Course;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.util.PropertySource;

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
//Trainer wird als Discriminator-Value für die User Tabelle gewählt
@DiscriminatorValue("Trainer")
//Join soll durch die ID
@PrimaryKeyJoinColumn(name = "id")
public class Trainer extends User{

    public Trainer(String username, String password, String name, Set<DayOfWeek> workingDays, Set<Course> courses){
        super(username, password, name, "Trainer");
        this.workingDays = workingDays;
        this.courses = courses;
    }

    @Basic(optional = false)
    private String role = "Trainer";

    @ManyToMany
    private Set<Course> courses;

    @OneToMany(mappedBy = "trainer")
    private Set<Appointment> appointments;

    //Arbeitstage werden mit DayOfWeek Objekten in Liste abgespeichert
    //Tabelle wird für die Arbeitstage wird erstellt mithilfe einer Joined-Table
    @ElementCollection(targetClass = DayOfWeek.class)
    @JoinTable(name = "workingDays", joinColumns = @JoinColumn(name = "trainer_id"))
    @Column(name = "workingDays", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> workingDays;

    //Kursnamen für gewählte Kurse werden als zusammengesetzter String für Thymeleaf ausgegeben
    public String getCourseNamesAsString() {
        return getCourses().stream().sorted().map(Course::getName).collect(Collectors.joining(", "));
    }

    //Arbeitstage für gewählte Arbeitstage werden als zusammengesetzter String für Thymeleaf ausgegeben
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
