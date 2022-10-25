package com.github.boman.game;

import com.github.boman.entity.*;
import com.github.boman.util.Box;

import java.util.List;

public interface Engine {
    void addUpdateEntity(Entity entity);

    void update();

    List<Entity> getUpdateableEntity();

    TileEntity[][] getBoard();

    boolean spawnBomb(Bomber player, int x, int y, int power);

    boolean spawnFire(Bomb bomb, int x, int y, Fire.State state);

    void spawnBomber(Bomber bomber);

    void spawnEnemy(Enemy enemy);

    void removeUpdateEntity(Entity e);

    TileEntity getTile(int x, int y);

    void setTile(TileEntity e, int x, int y);

    void addEntity(MoveableEntity e);

    void removeEntity(MoveableEntity e);

    Bomber getPlayer();

    void setPlayer(Bomber player);

    int getMapHeight();

    void setMapHeight(int mapHeight);

    int getMapWidth();

    void setMapWidth(int mapWidth);

    boolean validTile(int x, int y);

    Box getBoxAtTile(int x, int y);

    List<MoveableEntity> getEntities();

    boolean winLevel();
    boolean winGame();
    void reset();

    void nextLevel();

    int getEnemyCount();

    int getOnealCount();

    int getBalloomCount();

    void killEnemy(Enemy enemy);

    void togglePause();

    boolean isPaused();
}
