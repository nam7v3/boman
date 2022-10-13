package com.github.boman.game;

public class Duration {
    private int count;

    public Duration(int count) {
        this.count = count;
    }

    public Duration(Duration duration) {
        this.count = duration.count;
    }

    public boolean isNegative() {
        return count < 0;
    }

    public void minus() {
        this.count--;
    }

    public static Duration of(Duration duration) {
        return new Duration(duration);
    }

    public static Duration of(int count) {
        return new Duration(count);
    }
}
