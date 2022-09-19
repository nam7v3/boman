package com.github.boman.game;

import com.github.boman.entity.Entity;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class BomanRenderer {
    private Canvas canvas;
    private GraphicsContext gc;

    public BomanRenderer(Canvas canvas){
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
    }

    public void render(List<Entity> entities){
        this.gc.clearRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
        for(Entity entity:entities){
            entity.render(this.gc);
        }
    }
}
