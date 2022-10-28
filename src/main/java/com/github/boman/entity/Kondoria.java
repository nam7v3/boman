package com.github.boman.entity;

import com.github.boman.game.Engine;
import com.github.boman.sprites.Animation;
import com.github.boman.util.Box;
import javafx.scene.canvas.GraphicsContext;

public class Kondoria extends Enemy {
    public Kondoria(Engine engine, int x, int y) {
        super(engine, x, y);
        softPass = true;
        animation = Animation.getKondoriaAnimation();
        moveUp();
    }

    public Kondoria(Engine engine, Box curPos, double speed) {
        super(engine, curPos, speed);
        softPass = true;
        animation = Animation.getKondoriaAnimation();
        moveUp();
    }

    @Override
    public void update() {
        if (super.state == State.Up && engine.getTile(getTileX(), (int) (pos.getY() + pos.getH()) - 1) instanceof Wall) {
            moveDown();
            animation.setState(State.Down);
        }

        if (super.state == State.Down && engine.getTile(getTileX(), (int) (pos.getY()) + 1) instanceof Wall) {
            moveUp();
            animation.setState(State.Up);
        }
        if(attribute == Attribute.Dead){
            animation.setState(Attribute.Dead);
            stop();
            if(animation.animationDone()){
                engine.killEnemy(this);
            }
        }
        super.update();
    }

    @Override
    public void render(GraphicsContext gc, double x, double y, double scale) {
        gc.drawImage(
                animation.getImage(),
                x * scale,
                y * scale,
                ENEMY_WIDTH * scale,
                ENEMY_HEIGHT * scale
        );
    }

    @Override
    public void interactWith(Entity other) {
        if (other instanceof Fire) {
            attribute = Attribute.Dead;
        }
    }
}
