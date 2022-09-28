package com.github.boman.entity;

import com.github.boman.util.AABB;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Wall extends Entity {
    private final AABB box;

    private Image image;

    public Wall(double x, double y) {
        this.box = new AABB(x, y, ENTITY_WIDTH, ENTITY_HEIGHT);
        this.image = new Image(getClass().getResource("wall.png").toString());
    }

    @Override
    public void render(GraphicsContext gc) {
        //TODO: render bằng hình ảnh.
        gc.drawImage(this.image, box.getX(), box.getY(), box.getW(), box.getH());
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
