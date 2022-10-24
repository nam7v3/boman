package com.github.boman.entity;

import com.github.boman.game.Engine;
import com.github.boman.sprites.Sprite;

public class SpeedPowerup extends PowerupTile {
    public static final double BASE_SPEED = 0.005;
    public SpeedPowerup(Engine engine) {
        super(engine);
        this.img = Sprite.powerupSpeed;
    }

    @Override
    public boolean block() {
        return false;
    }

    @Override
    public void apply(Bomber bomber) {
        bomber.setSpeed(bomber.getSpeed() + BASE_SPEED);
        engine.setTile(new Grass(engine), bomber.getTileX(), bomber.getTileY());
    }
}
