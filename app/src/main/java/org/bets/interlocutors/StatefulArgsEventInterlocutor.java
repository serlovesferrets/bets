package org.bets.interlocutors;

import org.bets.builders.EventBuilder;
import org.bets.exceptions.AmountTooLongException;
import org.bets.exceptions.DateFormatException;
import org.bets.exceptions.EventNameTooLongException;
import org.bets.exceptions.MissingBuilderFieldException;
import org.bets.exceptions.NumberTooLongException;
import org.bets.exceptions.TimeFormatException;
import org.bets.types.Event;
import org.bets.types.parts.EventDate;
import org.bets.types.parts.EventTime;

public class StatefulArgsEventInterlocutor implements EventInterlocutor<StatefulArgsEventInterlocutor> {
    public StatefulArgsEventInterlocutor(String[] args) throws DateFormatException, TimeFormatException {
        // index 0 is the opt
        numberStr = args[1];
        eventNameStr = args[2];
        dateStr = args[3];
        timeStr = args[4];
        caseFirstStr = args[5];
        caseEvenStr = args[6];
        caseSecondStr = args[7];
    }

    private final EventBuilder builder = new EventBuilder();

    private final String numberStr;
    private final String eventNameStr;
    private final String dateStr;
    private final String timeStr;
    private final String caseFirstStr;
    private final String caseEvenStr;
    private final String caseSecondStr;

    @Override
    public StatefulArgsEventInterlocutor askDate() throws DateFormatException {
        builder.date(EventDate.parseDate(dateStr));
        return this;
    }

    @Override
    public StatefulArgsEventInterlocutor askFirstCase() throws AmountTooLongException, NumberFormatException {
        if (caseFirstStr.length() > 5) {
            throw new AmountTooLongException(
                    "La lunghezza di %d per il numero è troppo alta rispetto al massimo di 5! (Caso 1)"
                            .formatted(caseFirstStr.length()));
        }

        builder.caseFirst(Float.parseFloat(caseFirstStr));
        return this;
    }

    @Override
    public StatefulArgsEventInterlocutor askEvenCase() throws AmountTooLongException, NumberFormatException {
        if (caseEvenStr.length() > 5) {
            throw new AmountTooLongException(
                    "La lunghezza di %d per il numero è troppo alta rispetto al massimo di 5! (Caso X)"
                            .formatted(caseFirstStr.length()));
        }
        builder.caseEven(Float.parseFloat(caseEvenStr));
        return this;
    }

    @Override
    public StatefulArgsEventInterlocutor askSecondCase() throws AmountTooLongException, NumberFormatException {
        if (caseSecondStr.length() > 5) {
            throw new AmountTooLongException(
                    "La lunghezza di %d per il numero è troppo alta rispetto al massimo di 5! (Caso 2)"
                            .formatted(caseFirstStr.length()));
        }
        builder.caseSecond(Float.parseFloat(caseSecondStr));
        return this;
    }

    @Override
    public StatefulArgsEventInterlocutor askName() throws EventNameTooLongException {
        if (eventNameStr.length() > 20) {
            throw new EventNameTooLongException(
                    "La lunghezza di %d per il numero è troppo alta rispetto al massimo di 20! (nome evento)"
                            .formatted(eventNameStr.length()));
        }
        builder.eventName(eventNameStr);
        return this;
    }

    @Override
    public StatefulArgsEventInterlocutor askNumber() throws NumberTooLongException, NumberFormatException {
        if (numberStr.length() > 5) {
            throw new NumberTooLongException(
                    "La lunghezza di %d per il numero è troppo alta rispetto al massimo di 5! (Caso 2)"
                            .formatted(numberStr.length()));
        }

        builder.number(Integer.parseInt(numberStr));
        return this;
    }

    @Override
    public StatefulArgsEventInterlocutor askTime() throws TimeFormatException {
        builder.time(EventTime.parseTime(timeStr));
        return this;
    }

    @Override
    public Event build() throws MissingBuilderFieldException {
        return builder.build();
    }
}
