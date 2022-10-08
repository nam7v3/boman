package com.github.boman.entity;

import com.github.boman.game.Engine;
import com.github.boman.util.Box;

import java.time.Duration;

public class MoveableEntity extends Entity {
    protected Box pos;
    private Box futurePos;
    private double speed;

    enum State {
        Up,
        Down,
        Left,
        Right,
        Standing,
    }

    protected State state;

    public MoveableEntity(Engine engine, Box curPos, double speed) {
        super(engine);
        this.pos = curPos;
        this.futurePos = new Box(curPos);
        this.speed = speed;
        this.state = State.Standing;
    }

    public void moveUp() {
        state = State.Up;
    }

    public void moveDown() {
        state = State.Down;
    }

    public void moveLeft() {
        state = State.Left;
    }

    public void moveRight() {
        state = State.Right;
    }

    public void stop() {
        state = State.Standing;
    }

    @Override
    public void update(Duration t) {
        TileType[][] map = engine.getBoard();
        switch (state) {
            case Up -> {
                futurePos.setY(futurePos.getY() - speed);
                int lower = (int) (futurePos.getY() / engine.getTileHeight());
                int upper = (int) (pos.getY() / engine.getTileHeight());
                int left = (int) (pos.getX() / engine.getTileWidth());
                int right = (int) Math.ceil((pos.getX() + pos.getW()) / engine.getTileWidth()) - 1;

                loop:
                for (int i = left; i <= right; ++i) {
                    for (int j = upper; j >= lower; --j) {
                        if (map[j][i].blockTile()) {
                            futurePos.setY(Engine.TILE_HEIGHT * (j + 1));
                            break loop;
                        }
                    }
                }
            }
            case Down -> {
                futurePos.setY(futurePos.getY() + speed);
                int lower = (int) ((pos.getY() + pos.getH()) / engine.getTileHeight());
                int upper = (int) ((futurePos.getY() + futurePos.getH()) / engine.getTileHeight());
                int left = (int) (pos.getX() / engine.getTileWidth());
                int right = (int) Math.ceil((pos.getX() + pos.getW()) / engine.getTileWidth()) - 1;

                loop:
                for (int i = left; i <= right; ++i) {
                    for (int j = lower; j <= upper; ++j) {
                        if (map[j][i].blockTile()) {
                            futurePos.setY(Engine.TILE_HEIGHT * j - pos.getW());
                            break loop;
                        }
                    }
                }
            }
            case Left -> {
                futurePos.setX(futurePos.getX() - speed);
                int lower = (int) (pos.getY() / engine.getTileHeight());
                int upper = (int) Math.ceil((pos.getY() + pos.getH()) / engine.getTileHeight()) - 1;
                int left = (int) (futurePos.getX() / engine.getTileWidth());
                int right = (int) (pos.getX() / engine.getTileWidth());

                loop:
                for (int i = lower; i <= upper; ++i) {
                    for (int j = right; j >= left; --j) {
                        if (map[i][j].blockTile()) {
                            futurePos.setX(Engine.TILE_WIDTH * (j + 1));
                            break loop;
                        }
                    }
                }
            }
            case Right -> {
                futurePos.setX(futurePos.getX() + speed);
                int lower = (int) (pos.getY() / engine.getTileHeight());
                int upper = (int) Math.ceil((pos.getY() + pos.getW()) / engine.getTileHeight()) - 1;
                int left = (int) (pos.getX() / engine.getTileWidth());
                int right = (int) ((futurePos.getX() + futurePos.getW()) / engine.getTileWidth());

                loop:
                for (int i = lower; i <= upper; ++i) {
                    for (int j = left; j <= right; ++j) {
                        if (map[i][j].blockTile()) {
                            futurePos.setX(Engine.TILE_HEIGHT * j - pos.getW());
                            break loop;
                        }
                    }
                }
            }
            case Standing -> {
                futurePos.setX(pos.getX());
                futurePos.setY(pos.getY());
            }
        }
        pos = new Box(futurePos);
    }


    @Override
    public Box getBox() {
        return null;
    }
}
