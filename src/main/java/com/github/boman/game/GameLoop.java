package com.github.boman.game;

import javafx.animation.AnimationTimer;

public abstract class GameLoop extends AnimationTimer {
    long lastTime;

    @Override
    public void handle(long l) {
        long elapsed = l - lastTime;
        lastTime = l;
        tick(elapsed);
    }

    public abstract void tick(long elapsed);
}
