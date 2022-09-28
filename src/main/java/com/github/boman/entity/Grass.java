package com.github.boman.entity;

import com.github.boman.sprites.Sprite;
import com.github.boman.util.AABB;
import javafx.scene.canvas.GraphicsContext;

public class Grass extends Entity {
    public static final double WIDTH = 20;
    public static final double HEIGHT = 20;
    private final AABB box;

    public Grass(double x, double y) {
        this.box = new AABB(x, y, WIDTH, HEIGHT);
        img = Sprite.grass;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, box.getX(), box.getY(), box.getW(), box.getH());
    }

    @Override
    public AABB getBox() {
        return box;
    }
}
