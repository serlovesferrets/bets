package org.bets;

import org.bets.exceptions.TimeTooLongException;

public class BetTime {
    private String hour, minutes;

    public BetTime(String hour, String minutes) throws TimeTooLongException {
        if (hour.length() != 2 || minutes.length() != 2) {
            throw new TimeTooLongException("Errore nella lunghezza degli elementi! Segui il formato HHMM!");
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

    public static BetTime parseTime(String time) throws TimeTooLongException {
        var hour = time.substring(0, 2);
        var minutes = time.substring(2);

        return new BetTime(hour, minutes);
    }

    @Override
    public String toString() {
        return "%s%s".formatted(hour, minutes);
    }

    public String prettyString() {
        return "%s %s".formatted(hour, minutes);
    }
}
