package com.github.boman.entity;

import com.github.boman.game.Engine;
import com.github.boman.sprites.Animation;
import com.github.boman.util.Box;
import javafx.scene.canvas.GraphicsContext;
import java.util.LinkedList;

public class Oneal extends Enemy {
    LinkedList<Integer> queue = new LinkedList<>();
    State[][] trace = new State[engine.getMapHeight()][engine.getMapWidth()];
    public Oneal(Engine engine, int x, int y) {
        super(engine, x, y);
        animation = Animation.getOnealAnimation();
    }

    public Oneal(Engine engine, Box curPos, double speed) {
        super(engine, curPos, speed);
        animation = Animation.getOnealAnimation();
    }

    public boolean validMove(int x, int y) {
        return (x >= 0 && x <= 30 && y >= 0 && y <= 12);
    }

    public boolean outOfRange() {
        return Math.abs(pos.getX() - engine.getPlayer().getPos().getX())
                + Math.abs(pos.getY() - engine.getPlayer().getPos().getY()) > Enemy.IN_RANGE;
    }

    @Override
    public void update() {
        // TODO: Thuật toán tìm đường cho Oneal (BFS)
        for (int i = 0; i < engine.getMapHeight(); i++) {
            for (int j = 0; j < engine.getMapWidth(); j++) {
                trace[i][j] = null;
            }
        }
        queue.add(this.getTileX());
        queue.add(this.getTileY());
        trace[this.getTileY()][this.getTileX()] = State.Standing;

        while (!queue.isEmpty()) {
            int x = queue.poll();
            int y = queue.poll();
            if (validMove(x + 1, y) && !engine.getTile(x + 1, y).block() && trace[y][x + 1] == null) {
                queue.add(x + 1);
                queue.add(y);
                trace[y][x + 1] = State.Right;
            }
            if (validMove(x - 1, y) && !engine.getTile(x - 1, y).block() && trace[y][x - 1] == null) {
                queue.add(x - 1);
                queue.add(y);
                trace[y][x - 1] = State.Left;
            }
            if (validMove(x, y + 1) && !engine.getTile(x, y + 1).block() && trace[y + 1][x] == null) {
                queue.add(x);
                queue.add(y + 1);
                trace[y + 1][x] = State.Down;
            }
            if (validMove(x, y - 1) && !engine.getTile(x, y - 1).block() && trace[y - 1][x] == null) {
                queue.add(x);
                queue.add(y - 1);
                trace[y - 1][x] = State.Up;
            }
        }

        /* Debug
        for (int i = 0; i < engine.getMapHeight(); i++) {
            for (int j = 0; j < engine.getTileWidth(); j++) {
                System.out.print(trace[i][j]);
                System.out.print(' ');
            }
            System.out.print('\n');
        }
        */

        if (outOfRange() || trace[engine.getPlayer().getTileY()][engine.getPlayer().getTileX()] == null) {
            if (pos.getY() % engine.getTileHeight() != 0) {
                moveDown();
                animation.setState(State.Down);
            } else {
                if (super.state == State.Left && engine.getTile((int) ((pos.getX() + pos.getW()) / engine.getTileWidth()) - 1, getTileY()).block()) {
                    moveRight();
                    animation.setState(State.Right);
                }

                if (super.state == State.Right && engine.getTile((int) (pos.getX() / engine.getTileWidth()) + 1, getTileY()).block()) {
                    moveLeft();
                    animation.setState(State.Left);
                }
            }
        } else {

            State lastState = State.Left;
            int curX = engine.getPlayer().getTileX();
            int curY = engine.getPlayer().getTileY();
            if (trace[curY][curX] != null) {
                while (true) {
                    if (curX == this.getTileX() && curY == this.getTileY()) {
                        break;
                    }
                    lastState = trace[curY][curX];
                    switch (lastState) {
                        case Left -> curX++;
                        case Right -> curX--;
                        case Up -> curY++;
                        case Down -> curY--;
                    }
                }
            }

            // TODO: Moving, Speed up khi gặp Bomber
            Box other = new Box(getTileX() * engine.getTileWidth(), getTileY() * engine.getTileHeight(), engine.getTileWidth(), engine.getTileHeight());
            //System.out.println(pos);
            //System.out.println(other);
            if (!pos.inside(other)) {
                if (pos.getY() <= other.getY()) {
                    moveDown();
                    animation.setState(State.Down);
                } else if (pos.getY() >= other.getY()) {
                    moveUp();
                    animation.setState(State.Up);
                } else if (pos.getX() <= other.getX()) {
                    moveRight();
                    animation.setState(State.Right);
                } else {
                    moveLeft();
                    animation.setState(State.Left);
                }
            } else {
                switch (lastState) {
                    case Left -> moveLeft();
                    case Right -> moveRight();
                    case Up -> moveUp();
                    case Down -> moveDown();
                }
                animation.setState(super.state);
            }
        }

        super.update();
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
            engine.removeEntity(this);
        }
    }
}
