package com.github.boman.entity;

import com.github.boman.game.Engine;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Entity {
    protected Image img;
    protected Engine engine;

    public Entity(Engine engine) {
        this.engine = engine;
    }

    /**
     * Cập nhật thực thể theo thời gian.
     */
    public void update() {
    }

    /**
     * Render
     *
     * @param gc GraphicContext
     */
    public void render(GraphicsContext gc) {
    }

    /**
     * Xử lý va chạm các đồ vật khác.
     *
     * @param other Đồ vật khác
     */
    public void interactWith(Entity other) {

    }
    public abstract void render(GraphicsContext gc, double x, double y, double scale);
}
