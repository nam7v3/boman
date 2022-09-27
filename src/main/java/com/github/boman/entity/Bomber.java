package com.github.boman.entity;

import com.github.boman.event.EventListener;
import com.github.boman.util.AABB;
import com.github.boman.util.MoveableAABB;
import javafx.event.Event;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.time.Duration;


public class Bomber extends Entity implements EventListener {
    private static final double BOMBER_MAX_SPEED = 15000;
    private static final double BOMBER_ACCELERATION = 25000;

    private final MoveableAABB box;
    private final int lives = 3;
    private final int bombs = 1;

    public Bomber(double x, double y) {
        this.box = new MoveableAABB(x, y, ENTITY_WIDTH, ENTITY_HEIGHT, BOMBER_MAX_SPEED, BOMBER_ACCELERATION);
    }

    @Override
    public void update(Duration t) {
        box.update(t);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.BLUE);
        gc.fillRect(box.getX(), box.getY(), box.getW(), box.getH());
    }

    @Override
    public void onEvent(Event event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            KeyEvent keyEvent = (KeyEvent) event;
            switch (keyEvent.getCode()) {
                case W:
                    box.moveUp();
                    break;
                case A:
                    box.moveLeft();
                    break;
                case S:
                    box.moveDown();
                    break;
                case D:
                    box.moveRight();
                    break;
                case SPACE:
                    break;
            }
        } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            KeyEvent keyEvent = (KeyEvent) event;
            switch (keyEvent.getCode()) {
                case W:
                    box.stop();
                    break;
                case A:
                    box.stop();
                    break;
                case S:
                    box.stop();
                    break;
                case D:
                    box.stop();
                    break;
                case SPACE:
                    break;
            }
        }
    }

    @Override
    public void interactWith(Entity other) {
        if (other instanceof Wall) {
            this.box.clip(other.getBox());

        }
    }

    @Override
    public AABB getBox() {
        return box;
    }

    @Override
    public String toString() {
        return "Bomber{" +
                "box=" + box +
                ", lives=" + lives +
                ", bombs=" + bombs +
                '}';
    }
}
