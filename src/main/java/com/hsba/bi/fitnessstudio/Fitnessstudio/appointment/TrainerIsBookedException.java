package com.hsba.bi.fitnessstudio.Fitnessstudio.appointment;

public class TrainerIsBookedException extends RuntimeException{
    public TrainerIsBookedException(){
        super("Der Trainer ist zu diesem Zeitraum nicht verfügbar, bitte einen anderen Zeitraum auswählen");
    }

}
