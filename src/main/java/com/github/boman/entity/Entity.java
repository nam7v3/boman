package com.github.boman.entity;

import com.github.boman.util.AABB;
import javafx.scene.canvas.GraphicsContext;

import java.time.Duration;

public abstract class Entity {
    protected AABB box;

    /**
     * Cập nhật thực thể theo thời gian.
     */
    public void update(Duration t) {
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

    /**
     * Lấy box của đồ vật.
     *
     * @return
     */
    public abstract AABB getBox();
}
