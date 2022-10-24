package com.github.boman.entity;

import com.github.boman.game.Engine;
import javafx.scene.canvas.GraphicsContext;

public abstract class TileEntity extends Entity {
    public TileEntity(Engine engine) {
        super(engine);
    }

    @Override
    public void update() {
        super.update();
    }

    /**
     * Render Tile tại ô (x, y).
     *
     * @param gc Graphic Context.
     * @param x  Tọa độ x.
     * @param y  Tọa độ y.
     */
    public void render(GraphicsContext gc, int x, int y) {
        gc.drawImage(img,
                engine.getTileWidth() * x,
                engine.getTileHeight() * y,
                engine.getTileWidth(),
                engine.getTileHeight());
    }

    @Override
    public void interactWith(Entity other) {
        super.interactWith(other);
    }

    /**
     * Trả về true nếu MoveableEntity có thể đi qua được.
     * false nếu không thể đi qua được.
     *
     * @return
     */
    public abstract boolean block();

    @Override
    public void render(GraphicsContext gc, double x, double y, double scale) {
        gc.drawImage(img,
                x * scale,
                y * scale,
                scale,
                scale);

    }
}
