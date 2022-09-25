package com.github.boman.entity;

import com.github.boman.util.AABB;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Wall extends Entity {
    private static final double width = 20;
    private static final double height = 20;
    private final AABB box;

    public Wall(double x, double y) {
        this.box = new AABB(x, y, width, height);
    }

    @Override
    public void render(GraphicsContext gc) {
        //TODO: render bằng hình ảnh.
        gc.setFill(Color.BLACK);
        gc.fillRect(box.getX(), box.getY(), box.getW(), box.getH());
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
