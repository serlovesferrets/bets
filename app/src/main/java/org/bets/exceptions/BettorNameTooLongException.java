package org.bets.exceptions;

public class BettorNameTooLongException extends TooLongException {
    public BettorNameTooLongException(String message) {
        super(message);
    }
}
