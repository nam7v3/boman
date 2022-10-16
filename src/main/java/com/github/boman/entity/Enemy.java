package com.github.boman.entity;

import com.github.boman.game.Engine;
import com.github.boman.sprites.Animation;
import com.github.boman.util.Box;
import javafx.scene.canvas.GraphicsContext;

public class Enemy extends MoveableEntity {
    public static double ENEMY_WIDTH = 17;
    public static double ENEMY_HEIGHT = 17;
    public static double SPRITE_WIDTH = 20;
    public static double SPRITE_HEIGHT = 20;
    public static double ENEMY_SPEED = 1;
    private Animation animation = Animation.getEnemyAnimation();

    public enum Attribute {
        Alive,
        Dead,
    }

    public Enemy(Engine engine, int x, int y) {
        super(engine, new Box(x * engine.getTileWidth(), y * engine.getTileHeight(), ENEMY_WIDTH, ENEMY_HEIGHT), ENEMY_SPEED);
        moveLeft();
    }

    public Enemy(Engine engine, Box curPos, double speed) {
        super(engine, curPos, speed);
        moveLeft();
    }

    @Override
    public void update() {

        super.update();
        if (super.state == State.Left && engine.getTile((int) ((pos.getX() + pos.getW()) / engine.getTileWidth()) - 1, getTileY()).block()) {
            moveRight();
            animation.setState(State.Right);
        }

        if (super.state == State.Right && engine.getTile((int) (pos.getX() / engine.getTileWidth()) + 1, getTileY()).block()) {
            moveLeft();
            animation.setState(State.Left);
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(
                animation.getImage(),
                pos.getX(),
                pos.getY(),
                ENEMY_WIDTH,
                ENEMY_HEIGHT
        );
    }

    @Override
    public void interactWith(Entity other) {
        if (other instanceof Fire) {
            animation.setState(Attribute.Dead);
            engine.removeUpdateEntity(this);
            engine.removeEntity(this);
        }
    }
}
