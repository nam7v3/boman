package com.github.boman.entity;

import com.github.boman.event.EventListener;
import com.github.boman.game.Duration;
import com.github.boman.game.Engine;
import com.github.boman.sprites.Animation;
import com.github.boman.util.Box;
import com.github.boman.util.SoundEffects;
import javafx.event.Event;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;

public class Bomber extends MoveableEntity implements EventListener {
    private static final double BOMBER_WIDTH = 0.55;
    private static final double BOMBER_HEIGHT = 0.8;
    private static final double SPRITE_WIDTH = 0.9;
    private static final double SPRITE_HEIGHT = 0.8;
    private static final double BOMBER_SPEED = 0.08;
    private static final int INVINCIBLE_FRAME = 120;
    private Duration invincibleTime = null;
    private int lives = 3;
    private int maxBomb = 1;
    private int power = 1;
    private int curBomb = 0;
    private Animation animation = Animation.getPlayerAnimation();

    public enum Atrribute {
        Invincible,
        Normal,
        Dead,
    }

    private Atrribute bomberState = Atrribute.Normal;

    public Bomber(Engine engine, int x, int y) {
        //super(engine, new Box(x * engine.getTileWidth(), y * engine.getTileHeight(), BOMBER_WIDTH, BOMBER_HEIGHT), BOMBER_SPEED);
        super(engine,
                new Box(x, y, BOMBER_WIDTH, BOMBER_HEIGHT),
                BOMBER_SPEED);
    }

    public Bomber(Engine engine, double x, double y, double w, double h) {
        super(engine, new Box(x, y, w, h), BOMBER_SPEED);
    }

    @Override
    public void render(GraphicsContext gc, double x, double y, double scale) {
        if (bomberState == Atrribute.Invincible) {
            gc.setGlobalAlpha(0.2);
        }
        gc.drawImage(
                animation.getImage(),
                (x - (SPRITE_WIDTH - BOMBER_WIDTH) / 2 + 0.1) * scale,
                (y - (SPRITE_HEIGHT - BOMBER_HEIGHT) / 2) * scale,
                SPRITE_WIDTH * scale,
                SPRITE_HEIGHT * scale
        );

        if (bomberState == Atrribute.Invincible) {
            gc.setGlobalAlpha(1);
        }
    }

    @Override
    public void onEvent(Event event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            KeyEvent keyEvent = (KeyEvent) event;
            switch (keyEvent.getCode()) {
                case W -> {
                    moveUp();
                    animation.setState(State.Up);
                }
                case A -> {
                    moveLeft();
                    animation.setState(State.Left);
                }
                case S -> {
                    moveDown();
                    animation.setState(State.Down);
                }
                case D -> {
                    moveRight();
                    animation.setState(State.Right);
                }
                case SPACE -> placeBomb();
            }
        } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            KeyEvent keyEvent = (KeyEvent) event;
            switch (keyEvent.getCode()) {
                case W:
                    if (state == State.Up) {
                        stop();
                        animation.setState(State.Standing);
                    }
                    break;
                case A:
                    if (state == State.Left) {
                        stop();
                        animation.setState(State.Standing);
                    }
                    break;
                case S:
                    if (state == State.Down) {
                        stop();
                        animation.setState(State.Standing);
                    }
                    break;
                case D:
                    if (state == State.Right) {
                        stop();
                        animation.setState(State.Standing);
                    }
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
            damage();
        }
        if (other instanceof PowerupTile powerupTile) {
            powerupTile.apply(this);
            SoundEffects.instance.playSound(SoundEffects.SoundIndex.ITEM_PICKED);
        }
        if (other instanceof Enemy) {
            if (collision((MoveableEntity) other)) {
                damage();
            }
        }
    }

    public void damage() {
        if (bomberState == Atrribute.Normal && lives > 0) {
            bomberState = Atrribute.Invincible;
            lives--;
            SoundEffects.instance.playSound(SoundEffects.SoundIndex.BOMBER_DIED);
        }
        if (lives <= 0) {
            bomberState = Atrribute.Dead;
        }
    }

    @Override
    public void update() {
        switch (bomberState) {
            case Normal -> super.update();
            case Invincible -> {
                if (invincibleTime == null) {
                    invincibleTime = Duration.of(INVINCIBLE_FRAME);
                    super.update();
                    return;
                }
                invincibleTime.minus();
                if (invincibleTime.isNegative()) {
                    invincibleTime = null;
                    bomberState = Atrribute.Normal;
                }
                super.update();
            }
            case Dead -> {
                animation.setState(Bomber.Atrribute.Dead);
                if (animation.animationDone()) {
                    engine.removeUpdateEntity(this);
                    engine.removeEntity(this);
                }
            }
        }
    }

    public boolean isDead() {
        return lives <= 0;
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

    public int getMaxBomb() {
        return maxBomb;
    }

    public void setMaxBomb(int maxBomb) {
        this.maxBomb = maxBomb;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }
}
