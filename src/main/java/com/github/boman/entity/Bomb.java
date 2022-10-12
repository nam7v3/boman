package com.github.boman.entity;

import com.github.boman.game.Engine;
import com.github.boman.sprites.Sprite;

import java.time.Duration;

public class Bomb extends TileEntity {
    public static int DEFAULT_SECOND_WAIT = 2;
    private Duration timeLeft;
    private Bomber player;
    private int x, y;
    private int power;

    public Bomb(Engine engine, Bomber player, int x, int y, int power) {
        super(engine);
        this.player = player;
        this.img = Sprite.bomb;
        this.timeLeft = Duration.ofSeconds(DEFAULT_SECOND_WAIT);
        this.x = x;
        this.y = y;
        this.power = power;
    }

    @Override
    public void update(Duration t) {
        timeLeft = timeLeft.minus(t);
        if (timeLeft.isNegative()) {
            player.setCurBomb(player.getCurBomb() - 1);
            engine.explode(this);
        }
    }

    @Override
    public boolean block() {
        return true;
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

    public Bomber getPlayer() {
        return player;
    }

    public void setPlayer(Bomber player) {
        this.player = player;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}
