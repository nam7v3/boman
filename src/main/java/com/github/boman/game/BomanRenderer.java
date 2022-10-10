package com.github.boman.game;

import com.github.boman.entity.Entity;
import com.github.boman.entity.TileEntity;
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

    public void render(TileEntity[][] map, List<Entity> entities) {
        this.gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        this.gc.setFill(Color.GREEN);
        this.gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[0].length; ++j) {
                map[i][j].render(gc, j, i);
            }
        }

        for (Entity entity : entities) {
            entity.render(gc);
        }
    }
}
