package com.github.boman.entity;

import com.github.boman.game.Duration;
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
    private Duration timeLeft = null;

    public Doll(Engine engine, int x, int y) {
        super(engine, x, y);
        animation = Animation.getDollAnimation();
        dest = engine.getBoxAtTile(x, y);
    }

    public Doll(Engine engine, Box curPos, double speed) {
        super(engine, curPos, speed);
        animation = Animation.getDollAnimation();
        dest = engine.getBoxAtTile((int) curPos.getX(), (int) curPos.getY());
    }

    @Override
    public void update() {
        if (attribute == Attribute.Dead) {
            animation.setState(Attribute.Dead);
            stop();
            if (timeLeft != null) {
                if (timeLeft.isNegative() && animation.animationDone()) {
                    engine.killEnemy(this);
                    engine.spawnEnemy(new Oneal(engine, getTileX(), getTileY()));
                    engine.spawnEnemy(new Minvo(engine, getTileX(), getTileY()));
                } else {
                    timeLeft.minus();
                }
            }
            super.update();
            return;
        }
        if (!pos.inside(dest)) {
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
        if (trace[x][y] != null) {
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
        }

        state = dir;
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
        if (other instanceof Fire fire) {
            attribute = Attribute.Dead;
            timeLeft = Duration.of(fire.getTimeLeft());
        }
    }
}