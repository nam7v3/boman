package com.github.boman.entity;

import com.github.boman.game.Engine;
import com.github.boman.sprites.Animation;
import com.github.boman.util.Box;
import javafx.scene.canvas.GraphicsContext;

public class Balloom extends Enemy {
    public Balloom(Engine engine, int x, int y) {
        super(engine, x, y);
        animation = Animation.getBalloomAnimation();
    }

    public Balloom(Engine engine, Box curPos, double speed) {
        super(engine, curPos, speed);
        animation = Animation.getBalloomAnimation();
    }

    @Override
    public void update() {
        super.update();
        if (super.state == State.Left && engine.getTile((int) ((pos.getX() + pos.getW()) / engine.getTileWidth()) - 1, getTileY()).block()) {
            moveRight();
            animation.setState(State.Right);
        }

        if (super.state == State.Right && engine.getTile((int) (pos.getX() / engine.getTileWidth()) + 1, getTileY()).block()) {
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
                enemyWidth,
                enemyHeight
        );
    }

    @Override
    public void interactWith(Entity other) {
        if (other instanceof Fire) {
            animation.setState(Attribute.Dead);
            engine.removeEntity(this);
        }
    }
}
