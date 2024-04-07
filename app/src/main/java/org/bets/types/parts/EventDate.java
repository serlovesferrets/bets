package org.bets.types.parts;

import org.bets.exceptions.DateFormatException;

public class EventDate {
    private String year, month, day;

    public EventDate(String year, String month, String day) throws DateFormatException {
        if (year.length() != 4 || month.length() != 2 || day.length() != 2) {
            throw new DateFormatException("Errore nella lunghezza degli elementi! Segui il formato YYYYMMDD!");
        }

        this.year = year;
        this.month = month;
        this.day = day;
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }

    public static EventDate parseDate(String date) throws DateFormatException {
        var year = date.substring(4);
        var month = date.substring(2, 4);
        var day = date.substring(0, 2);

        return new EventDate(year, month, day);
    }

    @Override
    public String toString() {
        return "%s%s%s".formatted(day, month, year);
    }

    public String prettyString() {
        System.out.println("Year: " + year);
        System.out.println("Month: " + month);
        System.out.println("Day: " + day);
        return "%s/%s/%s".formatted(day, month, year);
    }
}
