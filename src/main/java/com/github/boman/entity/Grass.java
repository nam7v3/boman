package com.github.boman.entity;

import com.github.boman.game.Engine;

import java.time.Duration;

public class Grass extends TileEntity {
    public Grass(Engine engine) {
        super(engine);
    }

    @Override
    public void update(Duration t) {
        super.update(t);
    }

    @Override
    public void interactWith(Entity other) {
        super.interactWith(other);
    }

    @Override
    public boolean block() {
        return false;
    }
}
