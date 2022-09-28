package com.github.boman.entity;

import com.github.boman.sprites.Sprite;
import com.github.boman.util.AABB;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Objects;

public class Wall extends Entity {
    public static final double WIDTH = 20;
    public static final double HEIGHT = 20;
    private final AABB box;

    public Wall(double x, double y) {
        this.box = new AABB(x, y, WIDTH, HEIGHT);
        img = Sprite.wall;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, box.getX(), box.getY(), box.getW(), box.getH());
    }

    @Override
    public AABB getBox() {
        return box;
    }

    @Override
    public String toString() {
        return "Wall{" +
                "box=" + box +
                '}';
    }
}
