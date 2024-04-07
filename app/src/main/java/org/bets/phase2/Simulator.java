package org.bets.phase2;

import java.util.Random;

import org.bets.exceptions.UnreachableCode;
import org.bets.types.BetResult;

public class Simulator {
    private final int runCount;
    private final int betId;

    private static final Random random = new Random();

    public Simulator(int runCount, int betId) {
        this.runCount = runCount;
        this.betId = betId;
    }

    public static int randAmount() {
        return random.nextInt(90) + 10;
    }

    public static BetResult randResult() throws UnreachableCode {
        var result = random.nextInt(3);
        return switch (result) {
            case 0 -> BetResult.ONE;
            case 1 -> BetResult.EVEN;
            case 2 -> BetResult.TWO;
            default -> throw new UnreachableCode("Impossible!");
        };
    }
}
