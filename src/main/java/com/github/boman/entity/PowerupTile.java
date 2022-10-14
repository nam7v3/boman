package com.github.boman.entity;

import com.github.boman.game.Engine;
import javafx.scene.canvas.GraphicsContext;

public abstract class PowerupTile extends TileEntity {
    private boolean picked;
    public PowerupTile(Engine engine) {
        super(engine);
        picked = false;
    }
    @Override
    public void update() {
        super.update();
    }

    public void render(GraphicsContext gc, int x, int y) {
        gc.drawImage(img,
                engine.getTileWidth() * x,
                engine.getTileHeight() * y,
                engine.getTileWidth(),
                engine.getTileHeight());
    }

    public boolean isPicked() {
        return picked;
    }

    public void setPicked(boolean picked) {
        this.picked = picked;
    }

    @Override
    public void interactWith(Entity other) {
        super.interactWith(other);
    }

    @Override
    public boolean block() {
        return false;
    }

    public abstract void apply(Bomber bomber);
}
