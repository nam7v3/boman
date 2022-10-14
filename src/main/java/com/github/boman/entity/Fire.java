package com.github.boman.entity;

import com.github.boman.game.Duration;
import com.github.boman.game.Engine;
import com.github.boman.sprites.Animation;
import javafx.scene.canvas.GraphicsContext;

public class Fire extends TileEntity {
    public static int DEFAULT_FRAME_WAIT = 30;
    private Duration timeLeft;
    private int x;
    private int y;

    private Animation animation = Animation.getFireAnimation();

    public Fire(Engine engine, int x, int y, State state) {
        super(engine);
        this.timeLeft = Duration.of(DEFAULT_FRAME_WAIT);
        this.x = x;
        this.y = y;
        animation.setState(state);
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

    @Override
    public void render(GraphicsContext gc, int x, int y) {
        super.img = animation.getImage();
        super.render(gc, x, y);
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
