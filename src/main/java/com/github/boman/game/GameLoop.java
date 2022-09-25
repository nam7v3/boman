package com.github.boman.game;

import javafx.animation.AnimationTimer;

import java.time.Duration;

public abstract class GameLoop extends AnimationTimer {
    private long lastTime;
    private boolean init = true;

    @Override
    public void handle(long l) {
        if (init) {
            lastTime = l;
            init = false;
        }
        long elapsed = l - lastTime;
        lastTime = l;
        tick(Duration.ofNanos(elapsed));
    }

    public abstract void tick(Duration elapsed);
}
