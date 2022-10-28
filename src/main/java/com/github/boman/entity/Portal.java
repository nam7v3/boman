package com.github.boman.entity;

import com.github.boman.game.Engine;
import com.github.boman.sprites.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Portal extends TileEntity {
    private boolean open = false;

    public Portal(Engine engine) {
        super(engine);
        this.img = Sprite.portal;
        engine.addUpdateEntity(this);
    }

    @Override
    public void update() {
        if (engine.winLevel()) {
            open = true;
        }
    }

    @Override
    public void interactWith(Entity other) {
        if (other instanceof Bomber) {
            if (open) {
                engine.nextLevel();
            }
        }
    }

    @Override
    public void render(GraphicsContext gc, double x, double y, double scale) {
        if (open) {
            gc.setFill(Color.BLACK);
            gc.fillRect((x + 0.1 ) * scale,
                    (y + 0.1 ) * scale,
                    scale * 0.8,
                    scale * 0.8);
        } else {
            super.render(gc, x, y, scale);
        }
    }

    @Override
    public boolean block() {
        return false;
    }
}
