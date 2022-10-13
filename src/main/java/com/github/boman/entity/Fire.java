package com.github.boman.entity;

import com.github.boman.game.Duration;
import com.github.boman.game.Engine;
import com.github.boman.sprites.Sprite;

public class Fire extends TileEntity {
    public static int DEFAULT_FRAME_WAIT = 30;
    private Duration timeLeft;
    private int x;
    private int y;

    public Fire(Engine engine, int x, int y, State state) {
        super(engine);
        this.timeLeft = Duration.of(DEFAULT_FRAME_WAIT);
        this.x = x;
        this.y = y;
        switch (state) {
            case Middle -> this.img = Sprite.bombExploded;
            case Horizontal -> this.img = Sprite.explosionHorizontal;
            case Vertical -> this.img = Sprite.explosionVertical;
            case HLeft -> this.img = Sprite.explosionHorizontalLeftLast;
            case HRight -> this.img = Sprite.explosionHorizontalRightLast;
            case VUp -> this.img = Sprite.explosionVerticalTopLast;
            case VDown -> this.img = Sprite.explosionVerticalDownLast;
        }
    }

    @Override
    public void update() {
        timeLeft.minus();
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

    public enum State {
        Middle,
        Horizontal,
        Vertical,
        HLeft,
        HRight,
        VUp,
        VDown,
    }
}
