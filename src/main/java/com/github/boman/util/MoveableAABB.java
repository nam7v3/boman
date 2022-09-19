package com.github.boman.util;

/**
 * AABB di chuyen duoc
 */
public class MoveableAABB extends AABB{
    private Vec2 velocity;
    private Vec2 acceleration;
    private Vec2 destination;

    public MoveableAABB(double x, double y, double w, double h) {
        super(x, y, w, h);
        this.velocity = new Vec2(0, 0);
        this.acceleration = new Vec2(0, 0);
        this.destination = new Vec2(x + w / 2, y + h / 2);
    }

    public void move(Vec2 direction){
        direction.normalize();
    }
}
