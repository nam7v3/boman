package com.github.boman.game;

import com.github.boman.entity.*;

import java.util.List;

public interface Engine {
    void add(Entity entity);

    void update();

    List<Entity> getUpdateableEntity();

    TileEntity[][] getBoard();

    boolean spawnBomb(Bomber player, int x, int y, int power);

    boolean spawnFire(Bomb bomb, int x, int y, Fire.State state);

    double getTileHeight();

    double getTileWidth();

    void remove(Entity e);

    TileEntity getEntity(int x, int y);

    void setEntity(TileEntity e, int x, int y);
}
