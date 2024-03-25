package org.bets.interlocutors;

import java.util.ArrayList;

import org.bets.builders.BetBuilder;
import org.bets.exceptions.BetNotFoundException;
import org.bets.exceptions.BetNotInRangeException;
import org.bets.exceptions.BettorNameTooLongException;
import org.bets.exceptions.MissingBuilderFieldException;
import org.bets.exceptions.ResultFormatException;
import org.bets.types.Bet;
import org.bets.types.Event;

// ALSO verify that, when you try to set a bet's number, the bet actually exists

public class StatefulArgsBetInterlocutor implements BetInterlocutor<StatefulArgsBetInterlocutor> {
    private ArrayList<Event> events;
    private final BetBuilder builder = new BetBuilder();

    private final String betNumberStr;
    private final String betAmountStr;
    private final String betResultStr;
    private final String bettorNameStr;

    public StatefulArgsBetInterlocutor(String[] args, ArrayList<Event> events) {
        this.events = events;

        betNumberStr = args[1];
        betAmountStr = args[2];
        betResultStr = args[3];
        bettorNameStr = args[4];
    }

    @Override
    public StatefulArgsBetInterlocutor askBetNumber() throws NumberFormatException, BetNotFoundException {
        var number = Integer.parseInt(betNumberStr);

        if (!Event.numberExists(events, number)) {
            throw new BetNotFoundException("Bet number %d not found!".formatted(number));
        }

        builder.betNumber(number);
        return this;
    }

    @Override
    public StatefulArgsBetInterlocutor askBetAmount() throws BetNotInRangeException {
        var amount = Integer.parseInt(betAmountStr);
        builder.betAmount(amount);
        return this;
    }

    @Override
    public StatefulArgsBetInterlocutor askBetResult() throws ResultFormatException {
        var result = betResultStr;
        builder.betResult(result.charAt(0));
        return this;
    }

    @Override
    public StatefulArgsBetInterlocutor askBettorName() throws BettorNameTooLongException {
        builder.bettorName(bettorNameStr);
        return this;
    }

    @Override
    public Bet build() throws MissingBuilderFieldException {
        return builder.build();
    }
}
