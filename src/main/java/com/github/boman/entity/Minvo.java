package com.github.boman.entity;

import com.github.boman.game.Duration;
import com.github.boman.game.Engine;
import com.github.boman.sprites.Animation;
import com.github.boman.util.Box;
import javafx.scene.canvas.GraphicsContext;

public class Minvo extends Enemy {
    private Duration teleportTimer = Duration.of(-1);
    private int nextX;
    private int nextY;
    public Minvo(Engine engine, int x, int y) {
        super(engine, x, y);
        nextX = x;
        nextY = y;
        animation = Animation.getMinvoAnimation();
        state = State.Standing;
    }

    public Minvo(Engine engine, Box curPos, double speed) {
        super(engine, curPos, speed);
        nextX = (int) curPos.getX();
        nextY = (int) curPos.getY();
        animation = Animation.getMinvoAnimation();
        state = State.Standing;
    }

    @Override
    public void update() {
        if (teleportTimer.isNegative()) {
            pos = new Box(nextX, nextY, 1, 1);
            nextX = engine.getPlayer().getTileX();
            nextY = engine.getPlayer().getTileY();
            teleportTimer = Duration.of(180);
        } else {
            teleportTimer.minus();
        }
        if (attribute == Attribute.Dead){
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
