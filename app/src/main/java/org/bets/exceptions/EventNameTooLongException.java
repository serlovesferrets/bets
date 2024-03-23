package org.bets.exceptions;

public class EventNameTooLongException extends TooLongException {
    public EventNameTooLongException(String message) {
        super(message);
    }
}
