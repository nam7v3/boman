package com.github.boman.entity;

import com.github.boman.game.Engine;
import com.github.boman.sprites.Animation;
import com.github.boman.util.Box;
import javafx.scene.canvas.GraphicsContext;

public class Enemy extends MoveableEntity {
    protected double enemyWidth;
    protected double enemyHeight;
    public static double ENEMY_SPEED = 1;
    protected Animation animation;

    public enum Attribute {
        Alive,
        Dead,
    }

    public Enemy(Engine engine, int x, int y) {
        super(engine, new Box(x * engine.getTileWidth(), y * engine.getTileHeight(), engine.getTileWidth() * 0.9, engine.getTileHeight() * 0.9), ENEMY_SPEED);
        enemyWidth = engine.getTileWidth() * 0.9;
        enemyHeight = engine.getTileHeight() * 0.9;
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
