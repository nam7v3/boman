package com.github.boman.entity;

import com.github.boman.game.Engine;
import com.github.boman.sprites.Animation;
import com.github.boman.util.Box;
import javafx.scene.canvas.GraphicsContext;

import java.util.LinkedList;
import java.util.Queue;

public class Doll extends Enemy {
    private State[][] trace;
    private Queue<Integer> queue = new LinkedList<>();
    private Box dest;
    private boolean detectedBomber = false;

    public Doll(Engine engine, int x, int y) {
        super(engine, x, y);
        animation = Animation.getDollAnimation();
    }

    public Doll(Engine engine, Box curPos, double speed) {
        super(engine, curPos, speed);
        animation = Animation.getDollAnimation();
    }

    @Override
    public void update() {
        if (attribute == Attribute.Dead) {
            animation.setState(Attribute.Dead);
            stop();
            if (animation.animationDone()) {
                engine.killEnemy(this);
                engine.spawnEnemy(new Oneal(engine, 2, 2));
                engine.spawnEnemy(new Minvo(engine, 2, 2));
            }
            super.update();
            return;
        }
        if (detectedBomber && !pos.inside(dest)) {
            super.update();
            return;
        }
        Bomber bomber = engine.getPlayer();
        trace = new State[engine.getMapWidth()][engine.getMapHeight()];
        int x, y;
        queue.clear();
        queue.add(getTileX());
        queue.add(getTileY());
        while (!queue.isEmpty()) {
            x = queue.poll();
            y = queue.poll();

            if (x == bomber.getTileX() && y == bomber.getTileY()) {
                detectedBomber = true;
                break;
            }

            if (engine.validTile(x + 1, y) && trace[x + 1][y] == null && !engine.getTile(x + 1, y).block()) {
                queue.add(x + 1);
                queue.add(y);
                trace[x + 1][y] = State.Right;
            }
            if (engine.validTile(x - 1, y) && trace[x - 1][y] == null && !engine.getTile(x - 1, y).block()) {
                queue.add(x - 1);
                queue.add(y);
                trace[x - 1][y] = State.Left;
            }
            if (engine.validTile(x, y + 1) && trace[x][y + 1] == null && !engine.getTile(x, y + 1).block()) {
                queue.add(x);
                queue.add(y + 1);
                trace[x][y + 1] = State.Down;

            }
            if (engine.validTile(x, y - 1) && trace[x][y - 1] == null && !engine.getTile(x, y - 1).block()) {
                queue.add(x);
                queue.add(y - 1);
                trace[x][y - 1] = State.Up;
            }
        }
        x = bomber.getTileX();
        y = bomber.getTileY();
        State dir = State.Standing;
        if (trace[x][y] == null) {
            detectedBomber = false;
            if (super.state == State.Left && engine.getTile((int) (pos.getX() + pos.getW()) - 1, getTileY()).block()) {
                moveRight();
                dest = engine.getBoxAtTile((int) (pos.getX() + pos.getW()) - 1, getTileY());
                animation.setState(State.Right);
            }

            if (super.state == State.Right && engine.getTile((int) pos.getX() + 1, getTileY()).block()) {
                moveLeft();
                dest = engine.getBoxAtTile((int) pos.getX(), getTileY());
                animation.setState(State.Left);
            }
            if (attribute == Attribute.Dead) {
                animation.setState(Attribute.Dead);
                stop();
                if (animation.animationDone()) {
                    engine.killEnemy(this);
                }
            }
        } else {
            while (x != getTileX() || y != getTileY()) {
                dest = engine.getBoxAtTile(x, y);
                switch (trace[x][y]) {
                    case Down -> {
                        y = y - 1;
                        dir = State.Down;
                    }
                    case Up -> {
                        y = y + 1;
                        dir = State.Up;
                    }
                    case Left -> {
                        x = x + 1;
                        dir = State.Left;
                    }
                    case Right -> {
                        x = x - 1;
                        dir = State.Right;
                    }
                }
            }
            state = dir;
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
