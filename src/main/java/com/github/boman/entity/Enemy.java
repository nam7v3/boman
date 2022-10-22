package com.github.boman.entity;

import com.github.boman.game.Engine;
import com.github.boman.sprites.Animation;
import com.github.boman.util.Box;
import javafx.scene.canvas.GraphicsContext;

public class Enemy extends MoveableEntity {
    public static final double IN_RANGE = 200;
    public static double ENEMY_WIDTH = 17;
    public static double ENEMY_HEIGHT = 17;
    public static double SPRITE_WIDTH = 20;
    public static double SPRITE_HEIGHT = 20;
    public static double ENEMY_SPEED = 1;
    protected Animation animation;

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
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
    }

    @Override
    public void interactWith(Entity other) {
        super.interactWith(other);
    }
}
