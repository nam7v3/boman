package com.github.boman.entity;

import com.github.boman.game.Engine;
import com.github.boman.sprites.Sprite;

import java.time.Duration;

public class Fire extends TileEntity {
    public static int DEFAULT_SECOND_WAIT = 1;

    public enum State {
        Middle,
        Horizontal,
        Vertical,
        HLeft,
        HRight,
        VUp,
        VDown,
    }

    Duration timeLeft;
    private int x;
    private int y;

    public Fire(Engine engine, int x, int y, State state) {
        super(engine);
        this.timeLeft = Duration.ofSeconds(DEFAULT_SECOND_WAIT);
        this.x = x;
        this.y = y;
        switch (state) {
            case Middle -> this.img = Sprite.fireMiddle;
            case Horizontal -> this.img = Sprite.fireHorizontal;
            case Vertical -> this.img = Sprite.fireVertical;
            case HLeft -> this.img = Sprite.fireHLeft;
            case HRight -> this.img = Sprite.fireHRight;
            case VUp -> this.img = Sprite.fireVUp;
            case VDown -> this.img = Sprite.fireVDown;
        }
    }

    @Override
    public void update(Duration t) {
        timeLeft = timeLeft.minus(t);
        if (timeLeft.isNegative()) {
            engine.setEntity(new Grass(engine), x, y);
            engine.remove(this);
        }
    }

    @Override
    public void interactWith(Entity other) {
        super.interactWith(other);
    }

    @Override
    public boolean block() {
        return false;
    }

    public Duration getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(Duration timeLeft) {
        this.timeLeft = timeLeft;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
