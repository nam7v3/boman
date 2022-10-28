package com.github.boman.entity;

import com.github.boman.game.Engine;
import com.github.boman.sprites.Animation;
import com.github.boman.util.Box;
import javafx.scene.canvas.GraphicsContext;

public class Thwimp extends Enemy {
    public Thwimp(Engine engine, int x, int y) {
        super(engine, x, y);
        animation = Animation.getThwimpAnimation();
        state = State.Standing;
    }

    public Thwimp(Engine engine, Box curPos, double speed) {
        super(engine, curPos, speed);
        animation = Animation.getThwimpAnimation();
        state = State.Standing;
    }

    @Override
    public void update() {
        if (engine.getEnemyCount() == engine.getThwimpCount()) {
            attribute = Attribute.Dead;
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

    }
}
