package com.github.boman.entity;

import com.github.boman.game.Engine;
import com.github.boman.sprites.Sprite;
import com.github.boman.util.Box;
import javafx.scene.canvas.GraphicsContext;

public class Enemy extends MoveableEntity {
    public static double ENEMY_WIDTH = 17;
    public static double ENEMY_HEIGHT = 17;
    public static double SPRITE_WIDTH = 20;
    public static double SPRITE_HEIGHT = 20;
    public static double ENEMY_SPEED = 1;
    private int state = 0;

    public Enemy(Engine engine, int x, int y) {
        super(engine, new Box(x * engine.getTileWidth(), y * engine.getTileHeight(), ENEMY_WIDTH, ENEMY_HEIGHT), ENEMY_SPEED);
        img = Sprite.balloomLeft1;
    }

    public Enemy(Engine engine, Box curPos, double speed) {
        super(engine, curPos, speed);
        img = Sprite.balloomLeft1;
    }

    @Override
    public void update() {
        TileEntity[][] board = engine.getBoard();
        if (state == 0 && !board[getTileY()][(int) ((pos.getX() + pos.getW()) / engine.getTileWidth()) - 1].block()) {
            moveLeft();
        } else {
            state = 1;
        }

        if (state == 1 && !board[getTileY()][(int) (pos.getX() / engine.getTileWidth()) + 1].block()) {
            moveRight();
        } else {
            state = 0;
        }
        super.update();
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
    }

    @Override
    public void interactWith(Entity other) {
        if (other instanceof Fire) {
            engine.remove(this);
        }
    }
}
