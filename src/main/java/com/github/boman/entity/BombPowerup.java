package com.github.boman.entity;

import com.github.boman.game.Engine;
import com.github.boman.sprites.Sprite;

public class BombPowerup extends TileEntity {
    private boolean picked;

    public BombPowerup(Engine engine) {
        super(engine);
        picked = false;
        this.img = Sprite.powerupBombs;
    }

    public boolean isPicked() {
        return picked;
    }

    public void setPicked(boolean picked) {
        this.picked = picked;
    }

    public void increaseBomb(Bomber bomber) {
        bomber.setMaxBomb(bomber.getMaxBomb() + 1);
    }

    @Override
    public void update() {
        if (picked) {
            engine.remove(this);
        }
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
