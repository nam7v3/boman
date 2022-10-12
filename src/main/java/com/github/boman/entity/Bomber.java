package com.github.boman.entity;

import com.github.boman.event.EventListener;
import com.github.boman.game.Engine;
import com.github.boman.sprites.Sprite;
import com.github.boman.util.Box;
import javafx.event.Event;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;

import java.time.Duration;


public class Bomber extends MoveableEntity implements EventListener {
    public static final double BOMBER_WIDTH = 11;
    public static final double BOMBER_HEIGHT = 11;
    public static final double SPRITE_HEIGHT = 22;
    public static final double SPRITE_WIDTH = 22;
    private static final double BOMBER_SPEED = 0.08;
    private static final int INVINCIBLE_SECOND = 2;
    private Duration invincibleTime = Duration.ofSeconds(INVINCIBLE_SECOND);
    private int lives = 3;
    private final int maxBomb = 1;
    private int power = 1;
    private int curBomb = 0;

    private enum BomberState {
        Invincible,
        Normal,
    }

    private BomberState bomberState = BomberState.Normal;

    public Bomber(Engine engine, int x, int y) {
        super(engine, new Box(x * engine.getTileWidth(), y * engine.getTileHeight(), BOMBER_WIDTH, BOMBER_HEIGHT), BOMBER_SPEED);
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
                case W -> moveUp();
                case A -> moveLeft();
                case S -> moveDown();
                case D -> moveRight();
                case SPACE -> placeBomb();
            }
        } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            KeyEvent keyEvent = (KeyEvent) event;
            switch (keyEvent.getCode()) {
                case W:
                    if (state == State.Up) stop();
                    break;
                case A:
                    if (state == State.Left) stop();
                    break;
                case S:
                    if (state == State.Down) stop();
                    break;
                case D:
                    if (state == State.Right) stop();
                    break;
            }
        }
    }

    public void placeBomb() {
        if (curBomb >= maxBomb) return;
        if (engine.spawnBomb(this, getTileX(), getTileY(), power)) {
            curBomb++;
        }
    }

    @Override
    public void interactWith(Entity other) {
        if (other instanceof Fire) {
            if (bomberState == BomberState.Normal && lives > 0) {
                bomberState = BomberState.Invincible;
                lives--;
            }
            if (lives <= 0) {
                engine.remove(this);
            }
        }
    }

    @Override
    public void update(Duration t) {
        //System.out.println(bomberState);
        switch (bomberState) {
            case Normal -> super.update(t);
            case Invincible -> {
                if (invincibleTime == null) {
                    invincibleTime = Duration.ofSeconds(INVINCIBLE_SECOND);
                    super.update(t);
                    return;
                }
                invincibleTime = invincibleTime.minus(t);
                if (invincibleTime.isNegative()) {
                    invincibleTime = null;
                    bomberState = BomberState.Normal;
                }
                super.update(t);
            }
        }
    }

    public int getLives() {
        return lives;
    }

    public int getCurBomb() {
        return curBomb;
    }

    public void setCurBomb(int curBomb) {
        this.curBomb = curBomb;
    }

    public int getPower() {
        return power;
    }
}
