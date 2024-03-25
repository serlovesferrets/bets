package org.bets.types;

public class Bet {
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

    public Bet(int betNumber, String bettorName, int betAmount, BetResult result) {
        this.betNumber = betNumber;
        this.bettorName = bettorName;
        this.betAmount = betAmount;
        this.result = result;
    }

    public Bet(String serializedBet) {
        var parts = serializedBet.split(",");

        betNumber = Integer.parseInt(parts[0]);
        bettorName = parts[1];
        betAmount = Integer.parseInt(parts[2]);

        final var result = switch (parts[3].charAt(0)) {
            case 'x', 'X' -> BetResult.EVEN;
            case '1' -> BetResult.ONE;
            case '2' -> BetResult.TWO;
            default -> throw new IllegalArgumentException("Invalid argument " + parts[3].charAt(0) + "!!");
        };

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
