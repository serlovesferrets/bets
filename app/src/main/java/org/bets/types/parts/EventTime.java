package org.bets.types.parts;

import org.bets.exceptions.TimeFormatException;

public class EventTime {
    private String hour, minutes;

    public EventTime(String hour, String minutes) throws TimeFormatException {
        if (hour.length() != 2 || minutes.length() != 2) {
            throw new TimeFormatException("Errore nella lunghezza degli elementi! Segui il formato HHMM!");
        }

        this.hour = hour;
        this.minutes = minutes;
    }

    public String getHour() {
        return hour;
    }

    public String getMinutes() {
        return minutes;
    }

    public static EventTime parseTime(String time) throws TimeFormatException {
        var hour = time.substring(0, 2);
        var minutes = time.substring(2);

        return new EventTime(hour, minutes);
    }

    @Override
    public String toString() {
        return "%s%s".formatted(hour, minutes);
    }

    public String prettyString() {
        return "%s:%s".formatted(hour, minutes);
    }
}
