package com.github.boman.entity;

import com.github.boman.util.AABB;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Wall extends Entity {
    private AABB box;
    private static final double width = 20;
    private static final double height = 20;

    public Wall(double x, double y) {
        this.box = new AABB(x, y, width, height);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(box.getX(), box.getY(), box.getW(), box.getH());
    }
}
