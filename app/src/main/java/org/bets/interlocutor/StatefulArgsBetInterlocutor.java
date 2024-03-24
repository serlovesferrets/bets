package org.bets.interlocutor;

import org.bets.builders.BetBuilder;
import org.bets.exceptions.AmountTooLongException;
import org.bets.exceptions.DateFormatException;
import org.bets.exceptions.EventNameTooLongException;
import org.bets.exceptions.MissingBuilderFieldException;
import org.bets.exceptions.NumberTooLongException;
import org.bets.exceptions.TimeFormatException;
import org.bets.types.Bet;
import org.bets.types.parts.BetDate;
import org.bets.types.parts.BetTime;

public class StatefulArgsBetInterlocutor implements BetInterlocutor<StatefulArgsBetInterlocutor> {
    public StatefulArgsBetInterlocutor(String[] args) throws DateFormatException, TimeFormatException {
        // index 0 is the opt
        numberStr = args[1];
        eventNameStr = args[2];
        dateStr = args[3];
        timeStr = args[4];
        caseFirstStr = args[5];
        caseEvenStr = args[6];
        caseSecondStr = args[7];
    }

    private final String numberStr;
    private final String eventNameStr;
    private final String dateStr;
    private final String timeStr;
    private final String caseFirstStr;
    private final String caseEvenStr;
    private final String caseSecondStr;

    // Using reference types so they can be nullable instead of having zero values
    private Integer number;
    private String eventName;
    private BetDate date;
    private BetTime time;
    private Float caseFirst, caseEven, caseSecond;

    @Override
    public StatefulArgsBetInterlocutor askDate() throws DateFormatException {
        date = BetDate.parseDate(dateStr);
        return this;
    }

    @Override
    public StatefulArgsBetInterlocutor askFirstCase() throws AmountTooLongException, NumberFormatException {
        if (caseFirstStr.length() > 5) {
            throw new AmountTooLongException(
                    "La lunghezza di %d per il numero è troppo alta rispetto al massimo di 5! (Caso 1)"
                            .formatted(caseFirstStr.length()));
        }
        caseFirst = Float.parseFloat(caseFirstStr);
        return this;
    }

    @Override
    public StatefulArgsBetInterlocutor askEvenCase() throws AmountTooLongException, NumberFormatException {
        if (caseEvenStr.length() > 5) {
            throw new AmountTooLongException(
                    "La lunghezza di %d per il numero è troppo alta rispetto al massimo di 5! (Caso X)"
                            .formatted(caseFirstStr.length()));
        }
        caseEven = Float.parseFloat(caseEvenStr);
        return this;
    }

    @Override
    public StatefulArgsBetInterlocutor askSecondCase() throws AmountTooLongException, NumberFormatException {
        if (caseSecondStr.length() > 5) {
            throw new AmountTooLongException(
                    "La lunghezza di %d per il numero è troppo alta rispetto al massimo di 5! (Caso 2)"
                            .formatted(caseFirstStr.length()));
        }
        caseSecond = Float.parseFloat(caseSecondStr);
        return this;
    }

    @Override
    public StatefulArgsBetInterlocutor askName() throws EventNameTooLongException {
        if (eventNameStr.length() > 20) {
            throw new EventNameTooLongException(
                    "La lunghezza di %d per il numero è troppo alta rispetto al massimo di 20! (nome evento)"
                            .formatted(eventNameStr.length()));
        }
        eventName = eventNameStr;
        return this;
    }

    @Override
    public StatefulArgsBetInterlocutor askNumber() throws NumberTooLongException, NumberFormatException {
        if (numberStr.length() > 5) {
            throw new NumberTooLongException(
                    "La lunghezza di %d per il numero è troppo alta rispetto al massimo di 5! (Caso 2)"
                            .formatted(numberStr.length()));
        }

        number = Integer.parseInt(numberStr);
        return this;
    }

    @Override
    public StatefulArgsBetInterlocutor askTime() throws TimeFormatException {
        time = BetTime.parseTime(timeStr);
        return this;
    }

    @Override
    public Bet build() throws MissingBuilderFieldException {
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
        BetBuilder builder = null;
        try {
            builder = new BetBuilder()
                    .number(number)
                    .eventName(eventName)
                    .date(date)
                    .time(time)
                    .caseFirst(caseFirst)
                    .caseEven(caseEven)
                    .caseSecond(caseSecond);
        } catch (AmountTooLongException | EventNameTooLongException | NumberTooLongException e) {
            // This will literally never happen
            e.printStackTrace();
            System.exit(1);
        }
        return builder.build();
    }

}
