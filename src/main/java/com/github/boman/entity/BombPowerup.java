package com.github.boman.entity;

import com.github.boman.game.Engine;
import com.github.boman.sprites.Sprite;

public class BombPowerup extends PowerupTile {
    public BombPowerup(Engine engine) {
        super(engine);
        this.img = Sprite.powerupBombs;
    }

    @Override
    public boolean block() {
        return false;
    }

    @Override
    public void apply(Bomber bomber) {
        bomber.setMaxBomb(bomber.getMaxBomb() + 1);
        engine.setTile(new Grass(engine), bomber.getTileX(), bomber.getTileY());
    }


}
