package com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.service;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;

public class DayOfWeekTranslator {
    public static String[] dayOfWeekToGerman(){
        String[] daysOfWeekInGerman = new String[7];
        int counter = 0;
        for(DayOfWeek dayOfWeek : DayOfWeek.values()){
            daysOfWeekInGerman[counter] = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.GERMAN);
            counter++;
        }
        return daysOfWeekInGerman;
    }
}
