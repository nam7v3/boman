package com.github.boman.entity;

import com.github.boman.game.Engine;
import javafx.scene.canvas.GraphicsContext;

import java.time.Duration;

public class Fire extends Entity {
    public Fire(Engine engine) {
        super(engine);
    }

    @Override
    public void update(Duration t) {
        super.update(t);
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
    }

    @Override
    public void interactWith(Entity other) {
        super.interactWith(other);
    }
}
