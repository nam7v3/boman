package com.github.boman.game;

import com.github.boman.entity.Bomber;
import com.github.boman.entity.Entity;
import com.github.boman.entity.Fire;
import com.github.boman.entity.TileEntity;

import java.util.List;

public interface Engine {
    void add(Entity entity);

    void update();

    List<Entity> getUpdateableEntity();

    TileEntity[][] getBoard();

    boolean spawnBomb(Bomber player, int x, int y, int power);

    boolean spawnFire(int x, int y, Fire.State state);

    double getTileHeight();

    double getTileWidth();

    void remove(Entity e);

    TileEntity getEntity(int x, int y);

    void setEntity(TileEntity e, int x, int y);
}
