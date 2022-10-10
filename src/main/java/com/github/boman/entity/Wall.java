package com.github.boman.entity;

import com.github.boman.game.Engine;
import com.github.boman.sprites.Sprite;

import java.time.Duration;

public class Wall extends TileEntity {
    public Wall(Engine engine) {
        super(engine);
        this.img = Sprite.wall;
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
        return true;
    }
}
