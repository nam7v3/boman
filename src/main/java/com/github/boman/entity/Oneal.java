package com.github.boman.entity;

import com.github.boman.game.Engine;
import com.github.boman.sprites.Animation;
import com.github.boman.util.Box;
import javafx.scene.canvas.GraphicsContext;
import java.util.LinkedList;

public class Oneal extends Enemy {
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

    @Override
    public void update() {
        super.update();

        // TODO: Thuật toán tìm đường cho Oneal (BFS)
        TileEntity[][] board = engine.getBoard();
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(this.getTileX() - 1);
        queue.add(this.getTileY() - 1);
        State[][] trace = new State[13][31];
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

        State lastState = State.Left;
        int curX = engine.getPlayer().getTileX() - 1;
        int curY = engine.getPlayer().getTileY() - 1;
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

        // TODO: Moving, Speed up khi gặp Bomber
        //super.state = lastState;
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
