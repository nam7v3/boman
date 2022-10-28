package com.github.boman.entity;

import com.github.boman.game.Engine;

public class Barrier extends TileEntity{
    public Barrier(Engine engine) {
        super(engine);
    }

    @Override
    public boolean block() {
        return true;
    }
}
