package com.github.boman.entity;

import com.github.boman.game.Engine;
import com.github.boman.sprites.Sprite;

public class Brick extends TileEntity {
    public Brick(Engine engine) {
        super(engine);
        this.img = Sprite.brick;
    }

    @Override
    public void interactWith(Entity other) {
        super.interactWith(other);
    }

    @Override
    public boolean block() {
        return true;
    }
}
