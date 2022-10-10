package com.github.boman.game;

import com.github.boman.entity.Bomb;
import com.github.boman.entity.Bomber;
import com.github.boman.entity.Entity;
import com.github.boman.entity.TileEntity;

import java.time.Duration;
import java.util.List;

public interface Engine {
    void add(Entity entity);

    void update(Duration duration);

    List<Entity> getUpdateableEntity();

    TileEntity[][] getBoard();

    void explode(Bomb bomb);

    boolean spawnBomb(Bomber player, int x, int y, int power);

    double getTileHeight();

    double getTileWidth();

}
