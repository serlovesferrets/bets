package org.bets.types;

public class BetAcceptance {
    private int betNumber;
    private String bettorName;
    private int betAmount;
    private BetResult result;

    public String getBettorName() {
        return bettorName;
    }

    public int getBetAmount() {
        return betAmount;
    }

    public BetResult getResult() {
        return result;
    }

    public int getBetNumber() {
        return betNumber;
    }

    public BetAcceptance(int betNumber, String bettorName, int betAmount, BetResult result) {
        this.betNumber = betNumber;
        this.bettorName = bettorName;
        this.betAmount = betAmount;
        this.result = result;
    }

    public String serialized() {
        return "%d,%s,%d,%s".formatted(getBetNumber(), getBettorName(), getBetAmount(), getResult());
    }

    @Override
    public String toString() {
        return "Bet #%d by %s, worth $%d, status: %s".formatted(betNumber, bettorName, betAmount, result);
    }
}
