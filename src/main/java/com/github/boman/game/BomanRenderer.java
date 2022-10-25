package com.github.boman.game;

import com.github.boman.entity.Bomber;
import com.github.boman.entity.MoveableEntity;
import com.github.boman.entity.TileEntity;
import com.github.boman.util.Box;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.paint.Color;

import java.util.List;

public class BomanRenderer {
    private final Canvas canvas;
    private final GraphicsContext gc;

    private final int maxTileX = 25;
    private final int maxTileY = 20;

    private double scale = 1;
    private double curTileX, curTileY;

    public BomanRenderer(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        this.gc.setImageSmoothing(false);
        resize();
    }

    public void render(Engine engine) {
        double viewportStartX, viewportStartY;
        double viewportEndX, viewportEndY;
        Box viewport;
        TileEntity[][] map = engine.getBoard();
        List<MoveableEntity> entities = engine.getEntities();
        Bomber player = engine.getPlayer();
        if(engine.isPaused()){
            gc.setEffect(new GaussianBlur());
        }else {
            gc.setEffect(null);
        }

        viewportStartX = Math.max(player.getPos().getX() - maxTileX / 2.0, 0);
        viewportStartY = Math.max(player.getPos().getY() - maxTileY / 2.0, 0);
        viewportEndX = Math.min(viewportStartX + curTileX, engine.getMapWidth());
        viewportEndY = Math.min(viewportStartY + curTileY, engine.getMapHeight());

        viewport = new Box(
                viewportStartX,
                viewportStartY,
                viewportEndX - viewportStartX,
                viewportEndY - viewportStartY
        );
        this.gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        this.gc.setFill(Color.GREEN);
        this.gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());


        for (int i = (int) Math.floor(viewportStartY);
             i < Math.ceil(viewportEndY); ++i) {
            for (int j = (int) Math.floor(viewportStartX);
                 j < Math.ceil(viewportEndX); ++j) {

                map[i][j].render(gc,
                        j - viewportStartX,
                        i - viewportStartY,
                        scale);
            }
        }

        for (MoveableEntity entity : entities) {
            if (viewport.intersect(entity.getPos())) {
                entity.render(gc,
                        entity.getPos().getX() - viewport.getX(),
                        entity.getPos().getY() - viewport.getY(),
                        scale
                );
            }
        }
    }

    public void resizeWidth(double v) {
        canvas.setWidth(v);
        resize();
    }

    public void resizeHeight(double v) {
        canvas.setHeight(v);

        resize();
    }

    private void resize() {
        this.scale = Math.max(canvas.getWidth() / maxTileX, canvas.getHeight() / maxTileY);
        curTileX = Math.ceil(canvas.getWidth() / scale);
        curTileY = Math.ceil(canvas.getHeight() / scale);
    }
}
