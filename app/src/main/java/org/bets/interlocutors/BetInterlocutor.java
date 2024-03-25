package org.bets.interlocutors;

import org.bets.exceptions.BetNotFoundException;
import org.bets.exceptions.BetNotInRangeException;
import org.bets.exceptions.BettorNameTooLongException;
import org.bets.exceptions.MissingBuilderFieldException;
import org.bets.exceptions.ResultFormatException;
import org.bets.types.Bet;

public interface BetInterlocutor<T extends BetInterlocutor<T>> {
    T askBetNumber() throws BetNotFoundException;

    T askBettorName() throws BettorNameTooLongException;

    T askBetAmount() throws BetNotInRangeException;

    T askBetResult() throws ResultFormatException;

    Bet build() throws MissingBuilderFieldException;
}
