package com.github.boman.entity;

import com.github.boman.game.Engine;
import com.github.boman.sprites.Animation;
import com.github.boman.util.Box;
import javafx.scene.canvas.GraphicsContext;

public class Oneal extends Enemy {
    public Oneal(Engine engine, int x, int y) {
        super(engine, x, y);
        animation = Animation.getOnealAnimation();
    }

    public Oneal(Engine engine, Box curPos, double speed) {
        super(engine, curPos, speed);
        animation = Animation.getOnealAnimation();
    }

    @Override
    public void update() {
        super.update();
        if (super.state == State.Left && engine.getEntity((int) ((pos.getX() + pos.getW()) / engine.getTileWidth()) - 1, getTileY()).block()) {
            moveRight();
            animation.setState(State.Right);
        }

        if (super.state == State.Right && engine.getEntity((int) (pos.getX() / engine.getTileWidth()) + 1, getTileY()).block()) {
            moveLeft();
            animation.setState(State.Left);
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(
                animation.getImage(),
                pos.getX(),
                pos.getY(),
                ENEMY_WIDTH,
                ENEMY_HEIGHT
        );
    }

    @Override
    public void interactWith(Entity other) {
        if (other instanceof Fire) {
            animation.setState(Attribute.Dead);
            engine.remove(this);
        }
    }
}
