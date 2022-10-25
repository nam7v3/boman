package com.github.boman.entity;

import com.github.boman.game.Engine;
import com.github.boman.util.Box;
import javafx.scene.canvas.GraphicsContext;

public abstract class MoveableEntity extends Entity {
    protected Box pos;
    protected State state;
    private final Box futurePos;
    protected double speed;

    public MoveableEntity(Engine engine, Box curPos, double speed) {
        super(engine);
        this.pos = curPos;
        this.futurePos = new Box(curPos);
        this.speed = speed;
        this.state = State.Standing;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
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
    public void update() {
        interactWith(engine.getTile(getTileX(), getTileY()));
        engine.getTile(getTileX(), getTileY()).interactWith(this);
        switch (state) {
            // Xử lý va chạm khi đi lên
            case Up -> {
                futurePos.setY(futurePos.getY() - speed);
                int lower = (int) futurePos.getY();
                int upper = (int) pos.getY() - 1;
                int left = (int) pos.getX();
                int right = (int) Math.ceil(pos.getX() + pos.getW()) - 1;

                loop:
                for (int i = left; i <= right; ++i) {
                    for (int j = upper; j >= lower; --j) {
                        if (engine.getTile(i, j).block()) {
                            futurePos.setY(j + 1);
                            break loop;
                        }
                    }
                }
            }
            // Xử lý va chạm khi đi xuống
            case Down -> {
                futurePos.setY(futurePos.getY() + speed);
                int lower = (int) Math.ceil(pos.getY() + pos.getH());
                int upper = (int) (futurePos.getY() + futurePos.getH());
                int left = (int) pos.getX();
                int right = (int) Math.ceil(pos.getX() + pos.getW()) - 1;

                loop:
                for (int i = left; i <= right; ++i) {
                    for (int j = lower; j <= upper; ++j) {
                        if (engine.getTile(i, j).block()) {
                            futurePos.setY(j - pos.getH());
                            break loop;
                        }
                    }
                }
            }
            // Xử lý va chạm khi rẽ trái
            case Left -> {
                futurePos.setX(futurePos.getX() - speed);
                int lower = (int) pos.getY();
                int upper = (int) Math.ceil(pos.getY() + pos.getH()) - 1;
                int left = (int) futurePos.getX();
                int right = (int) pos.getX() - 1;

                loop:
                for (int j = lower; j <= upper; ++j) {
                    for (int i = right; i >= left; --i) {
                        if (engine.getTile(i, j).block()) {
                            futurePos.setX(i + 1);
                            break loop;
                        }
                    }
                }
            }
            // Xử lý va chạm khi rẽ phải
            case Right -> {
                futurePos.setX(futurePos.getX() + speed);
                int lower = (int) pos.getY();
                int upper = (int) Math.ceil(pos.getY() + pos.getH()) - 1;
                int left = (int) Math.ceil(pos.getX() + pos.getW());
                int right = (int) (futurePos.getX() + futurePos.getW());

                loop:
                for (int j = lower; j <= upper; ++j) {
                    for (int i = left; i <= right; ++i) {
                        if (engine.getTile(i, j).block()) {
                            futurePos.setX(i - pos.getW());
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

    /**
     * Lấy tọa độ X của MoveableEntity theo engine.
     *
     * @return
     */
    public int getTileX() {
        return (int) (pos.getX() + pos.getW() / 2);
    }

    /**
     * Lấy tọa độ y của MoveableEntity theo engine.
     *
     * @return
     */
    public int getTileY() {
        return (int) (pos.getY() + pos.getH() / 2);
    }

    public boolean collision(MoveableEntity other) {
        return this.pos.intersect(other.pos);
    }

    public Box getPos() {
        return pos;
    }

    public void setPos(Box pos) {
        this.pos = pos;
    }

    @Override
    public abstract void render(GraphicsContext gc, double x, double y, double scale);

    public enum State {
        Up,
        Down,
        Left,
        Right,
        Standing,
    }
}
