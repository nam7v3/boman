package com.github.boman.entity;

import com.github.boman.game.Engine;
import com.github.boman.sprites.Sprite;

import java.time.Duration;
import java.util.Random;

public class Brick extends TileEntity {
    public static int DEFAULT_SECOND_WAIT = 1;
    private int x;
    private int y;
    private boolean breaking;
    private Duration timeLeft;

    public Brick(Engine engine, int x, int y) {
        super(engine);
        this.x = x;
        this.y = y;
        this.timeLeft = Duration.ofSeconds(DEFAULT_SECOND_WAIT);
        this.breaking = false;
        this.img = Sprite.brick;
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

    public Duration getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(Duration timeLeft) {
        this.timeLeft = timeLeft;
    }

    public boolean isBreaking() {
        return breaking;
    }

    public void setBreaking(boolean breaking) {
        this.breaking = breaking;
    }

    @Override
    public void interactWith(Entity other) {
        super.interactWith(other);
    }

    @Override
    public void update(Duration t) {
        if (!breaking) {
            return;
        }
        timeLeft = timeLeft.minus(t);
        if (timeLeft.isNegative()) {
            Random rand = new Random();
            int key = rand.nextInt(100);
            if (key < 10) {
                engine.getBoard()[getY()][getX()] = new BombPowerup(engine);
            } else {
                engine.getBoard()[getY()][getX()] = new Grass(engine);
            }
            engine.remove(this);

        }
    }

    @Override
    public boolean block() {
        return true;
    }
}
