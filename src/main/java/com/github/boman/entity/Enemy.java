package com.github.boman.entity;

import com.github.boman.game.Engine;
import com.github.boman.sprites.Animation;
import com.github.boman.util.Box;

public abstract class Enemy extends MoveableEntity {
    public static double ENEMY_SPEED = 0.06;
    public static double ENEMY_HEIGHT = 0.9;
    public static double ENEMY_WIDTH = 0.9;
    public static double ENEMY_SPEEDUP = 0.12;
    protected static final double VISION = 10;
    protected Animation animation;

    public enum Attribute {
        Alive,
        Dead,
    }
    protected Attribute attribute;
    public Enemy(Engine engine, int x, int y) {
        super(engine, new Box(x,
                        y,
                        ENEMY_WIDTH,
                        ENEMY_HEIGHT),
                ENEMY_SPEED);
        attribute = Attribute.Alive;
        moveLeft();
    }

    public Enemy(Engine engine, Box curPos, double speed) {
        super(engine, curPos, speed);
        attribute = Attribute.Alive;
        moveLeft();
    }

    public boolean inRange() {
        return Math.abs(getTileX() - engine.getPlayer().getTileX())
                + Math.abs(getTileY() - engine.getPlayer().getTileY()) <= Enemy.VISION;
    }

    @Override
    public void update() {
        super.update();
    }


    @Override
    public void interactWith(Entity other) {
        super.interactWith(other);
    }
}
