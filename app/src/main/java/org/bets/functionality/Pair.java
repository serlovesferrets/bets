package org.bets.functionality;

public class Pair<T, U> {
    private final T left;
    private final U right;

    public Pair(T left, U right) {
        this.left = left;
        this.right = right;
    }

    public T getLeft() {
        return left;
    }

    public U getRight() {
        return right;
    }

    public <V> Pair<V, U> withLeft(V left) {
        return new Pair<>(left, right);
    }

    public <V> Pair<T, V> withRight(V right) {
        return new Pair<>(left, right);
    }

    @Override
    public String toString() {
        var sb = new StringBuilder("left:\n");
        sb.append(left.toString())
                .append("\nright:\n")
                .append(right.toString());
        return sb.toString();
    }
}
