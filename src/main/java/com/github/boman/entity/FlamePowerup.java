package com.github.boman.entity;

import com.github.boman.game.Engine;
import com.github.boman.sprites.Sprite;

public class FlamePowerup extends PowerupTile {
    public FlamePowerup(Engine engine) {
        super(engine);
        this.img = Sprite.powerupFlames;
    }

    @Override
    public boolean block() {
        return false;
    }

    @Override
    public void apply(Bomber bomber) {
        bomber.setPower(bomber.getPower() + 1);
        engine.setTile(new Grass(engine), bomber.getTileX(), bomber.getTileY());
    }
}
