package com.github.boman.entity;

public enum TileType {
    Wall,
    Brick,
    Grass,
    Player,
    Fire,
    Enemy;

    public boolean blockTile() {
        return this == Wall
                || this == Brick;
    }
}
