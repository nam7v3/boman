package com.github.boman.entity;

import com.github.boman.event.EventListener;
import com.github.boman.util.AABB;
import javafx.event.Event;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.time.Duration;


public class Bomber extends Entity implements EventListener {
    private AABB box;
    private int lives = 3;
    private int bombs = 1;
    private BomberState state = BomberState.STANDING;

    public Bomber() {
        this.box = new AABB(10, 10, 20, 20);
    }

    // Cac ham di chuyen dung MoveableAABB
    public void moveUp() {
        this.box.setY(this.box.getY() - 5);
        System.out.println("move up");
    }

    public void moveLeft() {
        this.box.setX(this.box.getX() - 5);
        System.out.println("move left");
    }

    public void moveRight() {
        this.box.setX(this.box.getX() + 5);
        System.out.println("move right");
    }

    public void moveDown() {
        this.box.setY(this.box.getY() + 5);
        System.out.println("move down");
    }

    @Override
    public void update(Duration t) {

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
                    moveUp();
                    break;
                case A:
                    moveLeft();
                    break;
                case S:
                    moveDown();
                    break;
                case D:
                    moveRight();
                    break;
                case SPACE:
                    break;
            }
        } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            KeyEvent keyEvent = (KeyEvent) event;
            switch (keyEvent.getCode()) {
                case W:

                    break;
                case A:
                    moveLeft();
                    break;
                case S:
                    moveDown();
                    break;
                case D:
                    moveRight();
                    break;
                case SPACE:
                    break;
            }
        }
    }
}
