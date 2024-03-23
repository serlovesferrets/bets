package org.bets;

import org.bets.exceptions.DateTooLongException;

public class BetDate {
    private String year, month, day;

    public BetDate(String year, String month, String day) throws DateTooLongException {
        if (year.length() != 4 || month.length() != 2 || day.length() != 2) {
            throw new DateTooLongException("Errore nella lunghezza degli elementi! Segui il formato YYYYMMDD!");
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

    public static BetDate parseDate(String date) throws DateTooLongException {
        var year = date.substring(0, 4);
        var month = date.substring(4, 6);
        var day = date.substring(6);

        return new BetDate(year, month, day);
    }

    @Override
    public String toString() {
        return "%s%s%s".formatted(year, month, day);
    }

    public String prettyString() {
        return "%s %s %s".formatted(year, month, day);
    }
}
