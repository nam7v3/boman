package com.github.boman.entity;

import com.github.boman.game.Duration;
import com.github.boman.game.Engine;
import com.github.boman.sprites.Animation;
import com.github.boman.sprites.Sprite;
import javafx.scene.canvas.GraphicsContext;

import java.util.Random;

public class Brick extends TileEntity {
    public static int DEFAULT_FRAME_WAIT = 30;
    private Animation animation = Animation.getBrickAnimation();
    private int x;
    private int y;
    private boolean containsPortal = false;

    public enum Attribute {
        Normal,
        Breaking,
    }

    public Attribute attribute;
    private Duration timeLeft;

    public Brick(Engine engine, int x, int y) {
        super(engine);
        this.x = x;
        this.y = y;
        this.timeLeft = Duration.of(DEFAULT_FRAME_WAIT);
        this.attribute = Attribute.Normal;
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

    public boolean isContainsPortal() {
        return containsPortal;
    }

    public void setContainsPortal(boolean containsPortal) {
        this.containsPortal = containsPortal;
    }

    public Duration getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(Duration timeLeft) {
        this.timeLeft = timeLeft;
    }

    public boolean isBreaking() {
        return attribute == Attribute.Breaking;
    }

    public void breakBrick() {
        this.attribute = Attribute.Breaking;
    }

    @Override
    public void interactWith(Entity other) {
        super.interactWith(other);
    }

    @Override
    public void update() {
        if (!isBreaking()) {
            return;
        }
        animation.setState(Attribute.Breaking);
        timeLeft.minus();
        if (timeLeft.isNegative()) {
            Random rand = new Random();
            int key = rand.nextInt(100);
            if (containsPortal) {
                engine.setTile(new Portal(engine), x, y);
            }
            else if (key < 10) {
                engine.setTile(new BombPowerup(engine), x, y);
            } else if (key < 20) {
                engine.setTile(new FlamePowerup(engine), x, y);
            } else if (key < 30) {
                engine.setTile(new SpeedPowerup(engine), x, y);
            } else {
                engine.setTile(new Grass(engine), x, y);
            }
            engine.removeUpdateEntity(this);
        }
    }

    @Override
    public void render(GraphicsContext gc, int x, int y) {
        super.img = animation.getImage();
        super.render(gc, x, y);
    }

    @Override
    public void render(GraphicsContext gc, double x, double y, double scale) {
        super.img = animation.getImage();
        super.render(gc, x, y, scale);
    }

    @Override
    public boolean block() {
        return true;
    }
}
