package com.github.boman.entity;

import com.github.boman.game.Duration;
import com.github.boman.game.Engine;
import com.github.boman.sprites.Sprite;

public class Bomb extends TileEntity {
    public static int DEFAULT_FRAME_WAIT = 120;
    private Duration timeLeft;
    private Bomber player;
    private int x, y;
    private int power;

    public Bomb(Engine engine, Bomber player, int x, int y, int power) {
        super(engine);
        this.player = player;
        this.img = Sprite.bomb;
        this.timeLeft = Duration.of(DEFAULT_FRAME_WAIT);
        this.x = x;
        this.y = y;
        this.power = power;
    }

    public void explode() {
        Fire fire = new Fire(engine, x, y, Fire.State.Middle);
        engine.add(fire);
        engine.setEntity(fire, x, y);
        // Lửa trái
        for (int left = 1; left <= power; left++) {
            int newX = x - left;
            Fire.State state = (left < power ? Fire.State.Horizontal : Fire.State.HLeft);
            if (!engine.spawnFire(newX, y, state)) {
                break;
            }
        }
        // Lửa phải
        for (int right = 1; right <= power; right++) {
            int newX = x + right;
            Fire.State state = (right < power ? Fire.State.Horizontal : Fire.State.HRight);
            if (!engine.spawnFire(newX, y, state)) {
                break;
            }
        }
        // Lửa trên
        for (int up = 1; up <= power; up++) {
            int newY = y - up;
            Fire.State state = (up < power ? Fire.State.Vertical : Fire.State.VUp);
            if (!engine.spawnFire(x, newY, state)) {
                break;
            }
        }
        // Lửa dưới
        for (int down = 1; down <= power; down++) {
            int newY = y + down;
            Fire.State state = (down < power ? Fire.State.Vertical : Fire.State.VDown);
            if (!engine.spawnFire(x, newY, state)) {
                break;
            }
        }
        engine.remove(this);
    }

    @Override
    public void update() {
        timeLeft.minus();
        if (timeLeft.isNegative()) {
            player.setCurBomb(player.getCurBomb() - 1);
            explode();
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
