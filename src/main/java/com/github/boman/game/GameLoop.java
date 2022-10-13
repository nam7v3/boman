package com.github.boman.game;

import javafx.animation.AnimationTimer;

public abstract class GameLoop extends AnimationTimer {
    private static final long nsPerFrame = 1_000_000_000 / 60;
    private long lastTime;
    private boolean init = true;

    @Override
    public void handle(long l) {
        if (init) {
            lastTime = l;
            init = false;
        }
        long elapsed = l - lastTime;
        if (elapsed > nsPerFrame) {
            for (int i = 0; i < (int) (elapsed / nsPerFrame); ++i) {
                tick();
            }
            lastTime = l;
        }
    }

    public abstract void tick();
}
