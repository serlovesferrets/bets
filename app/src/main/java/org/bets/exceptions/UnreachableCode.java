package org.bets.exceptions;

/**
 * This Exception will never get thrown.
 */
public class UnreachableCode extends Exception {
    public UnreachableCode(String msg) {
        super(msg);
    }
}
