package com.github.boman.util;

public class Vec2 {
    private double x;
    private double y;

    public Vec2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vec2 add(Vec2 other) {
        return new Vec2(this.x + other.x, this.y + other.y);
    }

    public Vec2 minus(Vec2 other) {
        return new Vec2(this.x - other.x, this.y - other.y);
    }

    public double dot(Vec2 other) {
        return this.x * other.x + this.y + other.y;
    }

    /**
     * Quay vec2 goc alpha rad
     *
     * @param alpha Goc quay theo rad
     */
    public void rotate(double alpha) {
        double x1 = Math.cos(alpha) * this.x - Math.sin(alpha) * this.y;
        double y1 = Math.sin(alpha) * this.x + Math.cos(alpha) * this.y;
        this.x = x1;
        this.y = y1;
    }

    public void normalize() {
        double sum = Math.sqrt(this.x * this.x + this.y * this.y);
        this.x /= sum;
        this.y /= sum;
    }
}
