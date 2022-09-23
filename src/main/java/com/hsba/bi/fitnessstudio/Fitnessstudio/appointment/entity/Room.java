package com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * @bcicek Klasse für Raum-Objekt
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Room {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue
    private Long id;

    @Basic(optional = false)
    private String roomName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "room")
    private List<Appointment> appointment;

    public Room(String roomName) {
        this.roomName = roomName;
    }
}
