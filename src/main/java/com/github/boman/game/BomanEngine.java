package com.github.boman.game;

import com.github.boman.entity.Bomber;
import com.github.boman.entity.Entity;
import com.github.boman.entity.TileType;
import com.github.boman.event.EventHandlerListener;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * Handle game logic.
 */
public class BomanEngine implements Engine {
    public double tileHeight = 20;
    public double tileWidth = 20;
    private List<Entity> entities;
    private TileType[][] board;
    private EventHandlerListener handler;
    private boolean started;

    public BomanEngine(EventHandlerListener handler) {
        this.entities = new ArrayList<>();
        this.handler = handler;
    }

    public void update(Duration t) {
        for (Entity entity : entities) {
            entity.update(t);
        }
    }

    public void add(Entity e) {
        this.entities.add(e);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void loadMap(String filename) {
        Scanner scanner = new Scanner(Objects.requireNonNull(getClass().getResourceAsStream(filename)));
        int level = scanner.nextInt();
        int height = scanner.nextInt();
        int width = scanner.nextInt();

        board = new TileType[height][width];
        scanner.nextLine();
        for (int i = 0; i < height; i++) {
            String line = scanner.nextLine();
            for (int j = 0; j < width; j++) {
                switch (line.charAt(j)) {
                    case '#' -> board[i][j] = TileType.Wall;
                    case ' ' -> board[i][j] = TileType.Grass;
                    case 'p' -> {
                        board[i][j] = TileType.Grass;
                        Bomber player = new Bomber(this, tileWidth * j, tileHeight * i);
                        handler.addListener(player);
                        add(player);
                    }
                    default -> board[i][j] = TileType.Grass;
                }
            }
        }
    }

    public TileType[][] getBoard() {
        return board;
    }

    @Override
    public double getTileHeight() {
        return tileHeight;
    }

    @Override
    public double getTileWidth() {
        return tileWidth;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}
