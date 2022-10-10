package com.github.boman.entity;

import com.github.boman.game.Engine;
import com.github.boman.util.Box;
import javafx.scene.canvas.GraphicsContext;

import java.time.Duration;

public class MoveableEntity extends Entity {
    protected Box pos;
    protected State state;
    private final Box futurePos;
    private final double speed;

    public MoveableEntity(Engine engine, Box curPos, double speed) {
        super(engine);
        this.pos = curPos;
        this.futurePos = new Box(curPos);
        this.speed = speed;
        this.state = State.Standing;
    }

    /**
     * Đi lên.
     */
    public void moveUp() {
        state = State.Up;
    }

    /**
     * Đi xuống.
     */
    public void moveDown() {
        state = State.Down;
    }

    /**
     * Đi trái.
     */
    public void moveLeft() {
        state = State.Left;
    }

    /**
     * Đi phải.
     */
    public void moveRight() {
        state = State.Right;
    }

    /**
     * Dừng lại.
     */
    public void stop() {
        state = State.Standing;
    }

    @Override
    public void update(Duration t) {
        TileEntity[][] map = engine.getBoard();
        switch (state) {
            // Xử lý va chạm khi đi lên
            case Up -> {
                futurePos.setY(futurePos.getY() - speed);
                int lower = (int) (futurePos.getY() / engine.getTileHeight());
                int upper = (int) (pos.getY() / engine.getTileHeight());
                int left = (int) (pos.getX() / engine.getTileWidth());
                int right = (int) Math.ceil((pos.getX() + pos.getW()) / engine.getTileWidth()) - 1;

                loop:
                for (int i = left; i <= right; ++i) {
                    for (int j = upper; j >= lower; --j) {
                        if (map[j][i].block()) {
                            futurePos.setY(engine.getTileHeight() * (j + 1));
                            break loop;
                        }
                    }
                }
            }
            // Xử lý va chạm khi đi xuống
            case Down -> {
                futurePos.setY(futurePos.getY() + speed);
                int lower = (int) ((pos.getY() + pos.getH()) / engine.getTileHeight());
                int upper = (int) ((futurePos.getY() + futurePos.getH()) / engine.getTileHeight());
                int left = (int) (pos.getX() / engine.getTileWidth());
                int right = (int) Math.ceil((pos.getX() + pos.getW()) / engine.getTileWidth()) - 1;

                loop:
                for (int i = left; i <= right; ++i) {
                    for (int j = lower; j <= upper; ++j) {
                        if (map[j][i].block()) {
                            futurePos.setY(engine.getTileHeight() * j - pos.getW());
                            break loop;
                        }
                    }
                }
            }
            // Xử lý va chạm khi rẽ trái
            case Left -> {
                futurePos.setX(futurePos.getX() - speed);
                int lower = (int) (pos.getY() / engine.getTileHeight());
                int upper = (int) Math.ceil((pos.getY() + pos.getH()) / engine.getTileHeight()) - 1;
                int left = (int) (futurePos.getX() / engine.getTileWidth());
                int right = (int) (pos.getX() / engine.getTileWidth());

                loop:
                for (int i = lower; i <= upper; ++i) {
                    for (int j = right; j >= left; --j) {
                        if (map[i][j].block()) {
                            futurePos.setX(engine.getTileWidth() * (j + 1));
                            break loop;
                        }
                    }
                }
            }
            // Xử lý va chạm khi rẽ phải
            case Right -> {
                futurePos.setX(futurePos.getX() + speed);
                int lower = (int) (pos.getY() / engine.getTileHeight());
                int upper = (int) Math.ceil((pos.getY() + pos.getW()) / engine.getTileHeight()) - 1;
                int left = (int) (pos.getX() / engine.getTileWidth());
                int right = (int) ((futurePos.getX() + futurePos.getW()) / engine.getTileWidth());

                loop:
                for (int i = lower; i <= upper; ++i) {
                    for (int j = left; j <= right; ++j) {
                        if (map[i][j].block()) {
                            futurePos.setX(engine.getTileWidth() * j - pos.getW());
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

    public void render(GraphicsContext gc) {
        gc.drawImage(img, pos.getX(), pos.getY(), pos.getW(), pos.getH());
    }

    /**
     * Lấy tọa độ X của MoveableEntity theo engine.
     *
     * @return
     */
    public int getTileX() {
        return (int) ((pos.getX() + pos.getW() / 2) / engine.getTileWidth());
    }

    /**
     * Lấy tọa độ y của MoveableEntity theo engine.
     *
     * @return
     */
    public int getTileY() {
        return (int) ((pos.getY() + pos.getH() / 2) / engine.getTileHeight());
    }

    enum State {
        Up,
        Down,
        Left,
        Right,
        Standing,
    }
}
