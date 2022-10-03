package com.github.boman.game;

import com.github.boman.entity.Entity;
import com.github.boman.entity.TileType;

import java.time.Duration;
import java.util.List;

public interface Engine {
    double TILE_HEIGHT = 20;
    double TILE_WIDTH = 20;

    void add(Entity entity);

    void update(Duration duration);

    List<Entity> getEntities();

    TileType[][] getBoard();

    double getTileHeight();

    double getTileWidth();
}
