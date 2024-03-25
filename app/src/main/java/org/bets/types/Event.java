package org.bets.types;

import java.io.Serializable;
import java.util.ArrayList;

import org.bets.exceptions.DateFormatException;
import org.bets.exceptions.TimeFormatException;
import org.bets.types.parts.EventDate;
import org.bets.types.parts.EventTime;

public class Event implements Serializable {
    public Event(int number, String eventName,
            EventDate date, EventTime time, float caseFirst,
            float caseEven, float caseSecond) {
        this.number = number;
        this.eventName = eventName;
        this.date = date;
        this.time = time;
        this.caseFirst = caseFirst;
        this.caseEven = caseEven;
        this.caseSecond = caseSecond;
    }

    public Event(String str) throws DateFormatException, TimeFormatException {
        var parts = str.split(",");

        number = Integer.parseInt(parts[0]);
        eventName = parts[1];
        date = EventDate.parseDate(parts[2]);
        time = EventTime.parseTime(parts[3]);
        caseFirst = Float.parseFloat(parts[4]);
        caseEven = Float.parseFloat(parts[5]);
        caseSecond = Float.parseFloat(parts[6]);
    }

    private int number;
    private String eventName;
    private EventDate date;
    private EventTime time;
    private float caseFirst, caseEven, caseSecond;

    public int getNumber() {
        return number;
    }

    public String getEventName() {
        return eventName;
    }

    public EventDate getDate() {
        return date;
    }

    public EventTime getTime() {
        return time;
    }

    public float getCaseFirst() {
        return caseFirst;
    }

    public float getCaseEven() {
        return caseEven;
    }

    public float getCaseSecond() {
        return caseSecond;
    }

    public static boolean numberExists(ArrayList<Event> events, int number) {
        return events.stream()
                .map(event -> event.getNumber())
                .anyMatch(eventNumber -> eventNumber == number);
    }

    @Override
    public String toString() {
        return """
                %s #%d, del %s, %s:
                - 1: %.2f
                - X: %.2f
                - 2: %.2f
                """
                .formatted(
                        getEventName(),
                        getNumber(),
                        getDate().prettyString(),
                        getTime().prettyString(),
                        getCaseFirst(),
                        getCaseEven(),
                        getCaseSecond());

    }

    public String serialized() {
        final var separator = ",";
        return new StringBuilder()
                .append(getNumber())
                .append(separator)
                .append(getEventName())
                .append(separator)
                .append(getDate())
                .append(separator)
                .append(getTime())
                .append(separator)
                .append(getCaseFirst())
                .append(separator)
                .append(getCaseEven())
                .append(separator)
                .append(getCaseSecond())
                .append(separator)
                .toString();
    }
}
