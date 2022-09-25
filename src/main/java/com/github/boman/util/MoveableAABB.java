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

    private boolean stop = true;

    public MoveableAABB(double x, double y, double w, double h, double maxSpeed, double acceleration) {
        super(x, y, w, h);
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
    }

    public void update(Duration t) {
        double dt = (double) t.getNano() / 10e9;
        // Cap nhat vi tri
        x = x + vx * dt + ax * dt * dt / 2;
        y = y + vy * dt + ay * dt * dt / 2;

        if (stop) {
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
        stop = true;
    }

    public void move(double ax, double ay) {
        this.ax = ax;
        this.ay = ay;
    }

    public void moveUp() {
        stop = false;
        move(0, -acceleration);
    }

    public void moveDown() {
        stop = false;
        move(0, acceleration);
    }

    public void moveLeft() {
        stop = false;
        move(-acceleration, 0);
    }

    public void moveRight() {
        stop = false;
        move(acceleration, 0);
    }
}
