package com.hsba.bi.fitnessstudio.Fitnessstudio.appointment;

public class RoomIsBookedException extends RuntimeException{

    public RoomIsBookedException(){
        super("Der Raum ist zu diesem Zeitraum nicht verfügbar, bitte einen anderen Zeitraum auswählen");
    }
}
