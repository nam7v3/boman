package com.github.boman.util;

public class AABB {
    protected double x;
    protected double y;
    protected double w;
    protected double h;

    public AABB(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public boolean intersect(AABB other) {
        return this.x < other.x + other.w &&
                this.x + this.w > other.x &&
                this.y < other.y + other.h &&
                this.y + this.h > other.y;
    }

    public void clip(AABB other) {
        if (this.x > other.x && this.x < other.x + other.w) {
            this.x += other.x + other.w - this.x;
        } else if (this.x + this.w > other.x && this.x + this.w < other.x + other.w) {
            this.x -= this.x + this.w - other.x;
        }
        if (this.y > other.y && this.y < other.y + other.h) {
            this.y += other.y + other.h - this.y;
        } else if (this.y + this.h > other.y && this.y + this.h < other.y + other.h) {
            this.y -= this.y + this.h - other.h;
        }
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getW() {
        return w;
    }

    public void setW(double w) {
        this.w = w;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    @Override
    public String toString() {
        return "AABB{" +
                "x=" + x +
                ", y=" + y +
                ", w=" + w +
                ", h=" + h +
                '}';
    }
}
