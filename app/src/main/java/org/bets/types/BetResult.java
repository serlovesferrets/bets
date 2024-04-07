package org.bets.types;

public enum BetResult {
    ONE,
    EVEN,
    TWO;

    @Override
    public String toString() {
        var index = ordinal();

        return switch (index) {
            case 0 -> "1";
            case 1 -> "x";
            case 2 -> "2";
            default -> throw new IllegalArgumentException("Unexpected value: " + index);
        };
    }

    public static BetResult fromChar(char c) {
        return switch (c) {
            case '1' -> ONE;
            case '2' -> TWO;
            case 'x', 'X' -> EVEN;
            default -> throw new IllegalArgumentException("Unexpected value: " + c);
        };
    }
}
