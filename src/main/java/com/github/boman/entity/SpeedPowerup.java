package com.github.boman.entity;

import com.github.boman.game.Engine;
import com.github.boman.sprites.Sprite;

public class SpeedPowerup extends PowerupTile {
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
        bomber.setSpeed(bomber.getSpeed() + MoveableEntity.BASE_SPEED);
        engine.setEntity(new Grass(engine), bomber.getTileX(), bomber.getTileY());
    }
}
