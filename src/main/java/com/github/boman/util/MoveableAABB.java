package com.github.boman.util;

import java.time.Duration;

/**
 * AABB di chuyen duoc
 */
public class MoveableAABB extends AABB {
    private double vx, vy;
    private double ax, ay;
    private double maxSpeed;
    private double acceleration;
    private enum State{
        MOVE_UP,
        MOVE_RIGHT,
        MOVE_LEFT,
        MOVE_DOWN,
        STANDING
    }

    private State state;

    public MoveableAABB(double x, double y, double w, double h, double maxSpeed, double acceleration) {
        super(x, y, w, h);
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
        this.state = State.STANDING;
    }

    public void update(Duration t) {
        double dt = (double) t.getNano() / 10e9;
        // Cap nhat vi tri
        x = x + vx * dt + ax * dt * dt / 2;
        y = y + vy * dt + ay * dt * dt / 2;

        if (state == State.STANDING) {
            vx = 0;
            vy = 0;
            ax = 0;
            ay = 0;
        } else {
            vx = Math.min(vx + ax * dt, maxSpeed);
            vy = Math.min(vy + ay * dt, maxSpeed);
        }
    }

    public void stop() {
        state = State.STANDING;
    }

    public void move(double ax, double ay) {
        this.ax = ax;
        this.ay = ay;
    }

    public void moveUp() {
        state = State.MOVE_UP;
        move(0, -acceleration);
    }

    public void moveDown() {
        state = State.MOVE_DOWN;
        move(0, acceleration);
    }

    public void moveLeft() {
        state = State.MOVE_LEFT;
        move(-acceleration, 0);
    }

    public void moveRight() {
        state = State.MOVE_RIGHT;
        move(acceleration, 0);
    }

    @Override
    public void clip(AABB other) {
        switch (state) {
            case MOVE_UP -> {
                this.y += other.y + other.h - this.y;
                this.vy = 0;
                this.ay = 0;
            }
            case MOVE_LEFT -> {
                this.x += other.x + other.w - this.x;
                this.vx = 0;
                this.ax = 0;
            }
            case MOVE_DOWN -> {
                this.y -= this.y + this.h - other.y;
                this.vy = 0;
                this.ay = 0;
            }
            case MOVE_RIGHT -> {
                this.x -= this.x + this.w - other.x;
                this.ax = 0;
                this.vx = 0;
            }
        }
    }
}
