package org.bets;

import org.bets.exceptions.*;

public class BetBuilder {
    private int number;
    private String eventName;
    private BetDate date;
    private BetTime time;
    private float caseFirst, caseEven, caseSecond;

    public BetBuilder number(int number) throws NumberTooLongException {
        if (number > 99999) {
            throw new NumberTooLongException("Numero troppo lungo: %d (max 5 cifre)".formatted(number));
        }

        this.number = number;
        return this;
    }

    public BetBuilder eventName(String eventName) throws EventNameTooLongException {
        if (eventName.length() > 20) {
            throw new EventNameTooLongException("Nome dell'evento troppo lungo (max 20 caratteri)");
        }

        this.eventName = eventName;
        return this;
    }

    public BetBuilder date(String year, String month, String date) throws DateFormatException {
        this.date = new BetDate(year, month, date);
        return this;
    }

    public BetBuilder date(BetDate date) {
        this.date = date;
        return this;
    }

    public BetBuilder time(String hour, String minutes) throws TimeFormatException {
        this.time = new BetTime(hour, minutes);
        return this;
    }

    public BetBuilder time(BetTime time) {
        this.time = time;
        return this;
    }

    public void checkAmountLength(float number, int maxLength, String amountName) throws AmountTooLongException {
        if (Float.toString(caseFirst).length() > maxLength) {
            throw new AmountTooLongException(
                    "%s troppo lungo: %.2f (max: %d)".formatted(amountName, number, maxLength));
        }
    }

    public BetBuilder caseFirst(float caseFirst) throws AmountTooLongException {
        checkAmountLength(caseFirst, 5, "Quota 1");
        this.caseFirst = caseFirst;
        return this;
    }

    public BetBuilder caseEven(float caseEven) throws AmountTooLongException {
        checkAmountLength(caseSecond, 5, "Quota X");
        this.caseEven = caseEven;
        return this;
    }

    public BetBuilder caseSecond(float caseSecond) throws AmountTooLongException {
        checkAmountLength(caseSecond, 5, "Quota 2");
        this.caseSecond = caseSecond;
        return this;
    }

    public Bet build() {
        return new Bet(
                number,
                eventName,
                date,
                time,
                caseFirst,
                caseEven,
                caseSecond);
    }
}
