package com.github.boman.entity;

import com.github.boman.game.Engine;
import com.github.boman.sprites.Sprite;

public class Portal extends TileEntity {
    public Portal(Engine engine) {
        super(engine);
        this.img = Sprite.portal;
    }

    @Override
    public boolean block() {
        return false;
    }
}
