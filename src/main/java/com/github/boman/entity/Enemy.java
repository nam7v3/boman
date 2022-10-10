package com.github.boman.entity;

import com.github.boman.game.Engine;
import com.github.boman.sprites.Sprite;
import com.github.boman.util.Box;
import javafx.scene.canvas.GraphicsContext;

import java.time.Duration;
import java.util.Random;

public class Enemy extends MoveableEntity {
    public static double ENEMY_WIDTH = 15;
    public static double ENEMY_HEIGHT = 15;
    public static double SPRITE_WIDTH = 20;
    public static double SPRITE_HEIGHT = 20;
    public static double ENEMY_SPEED = 0.05;
    Random rng = new Random();

    public Enemy(Engine engine, int x, int y) {
        super(engine, new Box(x * engine.getTileWidth(), y * engine.getTileHeight(), ENEMY_WIDTH, ENEMY_HEIGHT), ENEMY_SPEED);
        img = Sprite.enemy;
    }

    public Enemy(Engine engine, Box curPos, double speed) {
        super(engine, curPos, speed);
        img = Sprite.enemy;
    }

    @Override
    public void update(Duration t) {
        // TODO
        switch (rng.nextInt() % 3) {
            case 0 -> stop();
            case 1 -> moveRight();
            case 2 -> moveLeft();

        }
        super.update(t);
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
    }
}
