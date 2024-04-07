package org.bets.phase2;

import java.util.ArrayList;

import org.bets.types.Bet;
import org.bets.types.BetResult;
import org.bets.types.Event;

public class Elaborator {
    private final Event event;
    private final ArrayList<Bet> bets;

    public Elaborator(ArrayList<Bet> bets, Event event) {
        this.bets = bets;
        this.event = event;
    }

    private int actualGainPercentage(BetResult result) {
        var wins = 0;
        var losses = 0;
        var all = 0;

        for (var bet : bets) {
            if (bet.getResult() == result) {
                wins++;
            } else {
                losses++;
            }
            all++;
        }

        var gainPercentage = (wins - losses) * 100 / all;
        return gainPercentage;
    }

    private int theoreticalGainPercentage() {
        var quoteFst = event.getCaseFirst();
        var quoteEven = event.getCaseEven();
        var quoteSnd = event.getCaseSecond();

        System.out.println(quoteFst);
        System.out.println(quoteEven);
        System.out.println(quoteSnd);
        var theoreticalGainPercentage = Math.round(100 / quoteFst + 100 / quoteEven + 100 / quoteSnd - 100);

        return theoreticalGainPercentage;
    }

    public void run(BetResult result) {
        System.out.println("In caso %s, la percentuale di guadagno è del "
                .formatted(result.toString()) + actualGainPercentage(result) + "%.");
    }

    public void run() {
        System.out.println("La percentuale di guadagno teorica è del " + theoreticalGainPercentage() + "%.");
        run(BetResult.ONE);
        run(BetResult.EVEN);
        run(BetResult.TWO);
    }
}
