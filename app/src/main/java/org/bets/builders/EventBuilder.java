package org.bets.builders;

import org.bets.exceptions.*;
import org.bets.types.Event;
import org.bets.types.parts.EventDate;
import org.bets.types.parts.EventTime;

public class EventBuilder {
    // reference types so they can be nullable
    private Integer number;
    private String eventName;
    private EventDate date;
    private EventTime time;
    private Float caseFirst, caseEven, caseSecond;

    public EventBuilder number(int number) throws NumberTooLongException {
        if (number > 99999) {
            throw new NumberTooLongException("Numero troppo lungo: %d (max 5 cifre)".formatted(number));
        }

        this.number = number;
        return this;
    }

    public EventBuilder eventName(String eventName) throws EventNameTooLongException {
        if (eventName.length() > 20) {
            throw new EventNameTooLongException("Nome dell'evento troppo lungo (max 20 caratteri)");
        }

        this.eventName = eventName;
        return this;
    }

    public EventBuilder date(String year, String month, String date) throws DateFormatException {
        this.date = new EventDate(year, month, date);
        return this;
    }

    public EventBuilder date(EventDate date) {
        this.date = date;
        return this;
    }

    public EventBuilder time(String hour, String minutes) throws TimeFormatException {
        this.time = new EventTime(hour, minutes);
        return this;
    }

    public EventBuilder time(EventTime time) {
        this.time = time;
        return this;
    }

    public void checkAmountLength(float number, int maxLength, String amountName) throws AmountTooLongException {
        if (Float.toString(number).length() > maxLength) {
            throw new AmountTooLongException(
                    "%s troppo lungo: %.2f (max: %d)".formatted(amountName, number, maxLength));
        }
    }

    public EventBuilder caseFirst(float caseFirst) throws AmountTooLongException {
        checkAmountLength(caseFirst, 5, "Quota 1");
        this.caseFirst = caseFirst;
        return this;
    }

    public EventBuilder caseEven(float caseEven) throws AmountTooLongException {
        checkAmountLength(caseEven, 5, "Quota X");
        this.caseEven = caseEven;
        return this;
    }

    public EventBuilder caseSecond(float caseSecond) throws AmountTooLongException {
        checkAmountLength(caseSecond, 5, "Quota 2");
        this.caseSecond = caseSecond;
        return this;
    }

    public Event build() throws MissingBuilderFieldException {
        if (number == null || eventName == null || date == null || time == null
                || caseFirst == null || caseEven == null
                || caseSecond == null) {
            throw new MissingBuilderFieldException("""
                                  One or more fields was null! Status:
                                  - number: %d,
                                  - eventName: %s,
                                  - date: %s,
                                  - time: %s,
                                  - caseFirst: %.2f,
                                  - caseEven: %.2f,
                                  - caseSecond: %.2f
                    """.formatted(number, eventName, date.toString(),
                    time.toString(), caseFirst, caseEven, caseSecond));
        }
        return new Event(number, eventName, date, time, caseFirst, caseEven, caseSecond);
    }
}
