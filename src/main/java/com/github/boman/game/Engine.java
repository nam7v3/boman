package com.github.boman.game;

import com.github.boman.entity.*;

import java.time.Duration;
import java.util.List;

public interface Engine {
    void add(Entity entity);

    void update(Duration duration);

    List<Entity> getUpdateableEntity();

    TileEntity[][] getBoard();

    boolean spawnBomb(Bomber player, int x, int y, int power);

    double getTileHeight();

    double getTileWidth();

    void remove(Entity e);

    TileEntity getEntity(int x, int y);

    void setEntity(TileEntity e, int x, int y);
}
