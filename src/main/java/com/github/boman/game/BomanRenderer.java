package com.github.boman.game;

import com.github.boman.entity.Entity;
import com.github.boman.entity.TileType;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class BomanRenderer {
    private final Canvas canvas;
    private final GraphicsContext gc;

    public BomanRenderer(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
    }

    public void render(TileType[][] map, List<Entity> entities) {
        this.gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        this.gc.setFill(Color.LIME);
        this.gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[0].length; ++j) {
                map[i][j].render(gc, j * Engine.TILE_WIDTH, i * Engine.TILE_HEIGHT, Engine.TILE_WIDTH, Engine.TILE_HEIGHT);
            }
        }

        for (Entity entity : entities) {
            entity.render(this.gc);
        }
    }
}
