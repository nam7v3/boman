package com.github.boman.entity;

import com.github.boman.event.EventListener;
import com.github.boman.game.Engine;
import com.github.boman.sprites.Sprite;
import com.github.boman.util.Box;
import javafx.event.Event;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;


public class Bomber extends MoveableEntity implements EventListener {
    private static final double BOMBER_SPEED = 0.08;
    public static final double BOMBER_WIDTH = 11;
    public static final double BOMBER_HEIGHT = 11;
    public static final double SPRITE_HEIGHT = 22;
    public static final double SPRITE_WIDTH = 22;
    private final int lives = 3;
    private final int bombs = 1;

    public Bomber(Engine engine, double x, double y) {
        super(engine, new Box(x, y, BOMBER_WIDTH, BOMBER_HEIGHT), BOMBER_SPEED);
        img = Sprite.bomberDown;
    }

    public Bomber(Engine engine, double x, double y, double w, double h) {
        super(engine, new Box(x, y, w, h), BOMBER_SPEED);
        img = Sprite.bomberDown;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, pos.getX() - (SPRITE_WIDTH - pos.getW()) / 2, pos.getY() - (SPRITE_HEIGHT - pos.getH()), SPRITE_WIDTH, SPRITE_HEIGHT);
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
                    stop();
                    break;
                case A:
                    stop();
                    break;
                case S:
                    stop();
                    break;
                case D:
                    stop();
                    break;
                case SPACE:
                    break;
            }
        }
    }
}
