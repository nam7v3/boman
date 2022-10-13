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

    public void explode(Bomb bomb) {
        int x = bomb.getX();
        int y = bomb.getY();
        int power = bomb.getPower();
        engine.remove(bomb);
        Fire fire = new Fire(engine, x, y, Fire.State.Middle);
        engine.add(fire);
        engine.getBoard()[y][x] = fire;
        // Lửa trái
        for (int left = 1; left <= power; left++) {
            int newX = x - left;
            Fire.State state = (left < power ? Fire.State.Horizontal : Fire.State.HLeft);
            if (!canSpawnFire(newX, y, state)) {
                break;
            }
            
        }
        // Lửa phải
        for (int right = 1; right <= power; right++) {
            int newX = x + right;
            Fire.State state = (right < power ? Fire.State.Horizontal : Fire.State.HRight);
            if (!canSpawnFire(newX, y, state)) {
                break;
            }
        }
        // Lửa trên
        for (int up = 1; up <= power; up++) {
            int newY = y - up;
            Fire.State state = (up < power ? Fire.State.Vertical : Fire.State.VUp);
            if (!canSpawnFire(x, newY, state)) {
                break;
            }
        }
        // Lửa dưới
        for (int down = 1; down <= power; down++) {
            int newY = y + down;
            Fire.State state = (down < power ? Fire.State.Vertical : Fire.State.VDown);
            if (!canSpawnFire(x, newY, state)) {
                break;
            }
        }
    }
    
    public void breakBrick(Brick brick) {
        brick.setBreaking(true);
        engine.add(brick);
    }
    
    public boolean canSpawnFire(int x, int y, Fire.State state) {
        if (engine.getBoard()[y][x] instanceof Wall) {
            return false;
        }
        if (engine.getBoard()[y][x] instanceof Brick) {
            breakBrick((Brick) engine.getBoard()[y][x]);
            return false;
        }
        Fire fire = new Fire(engine, x, y, state);
        engine.add(fire);
        engine.getBoard()[y][x] = fire;
        return true;
    }

    @Override
    public void update(Duration t) {
        timeLeft = timeLeft.minus(t);
        if (timeLeft.isNegative()) {
            player.setCurBomb(player.getCurBomb() - 1);
            explode(this);
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
