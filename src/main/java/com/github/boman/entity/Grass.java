package com.github.boman.entity;

import com.github.boman.game.Engine;

public class Grass extends TileEntity {
    public Grass(Engine engine) {
        super(engine);
    }

    @Override
    public boolean block() {
        return false;
    }
}
