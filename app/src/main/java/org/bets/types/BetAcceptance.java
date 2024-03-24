package org.bets.types;

public class BetAcceptance {
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

    public BetAcceptance(String bettorName, Integer betAmount, BetResult result) {
        this.bettorName = bettorName;
        this.betAmount = betAmount;
        this.result = result;
    }

    public String serialized() {
        return "%s,%d,%s".formatted(getBettorName(), getBetAmount(), getResult());
    }

    @Override
    public String toString() {
        return "Bet by %s, worth $%d, status: %s".formatted(bettorName, betAmount, result);
    }
}
