package com.github.boman.entity;

import com.github.boman.sprites.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public enum TileType {
    Wall(Sprite.wall),
    Brick(Sprite.wall),
    Grass(Sprite.grass);
    private final Image image;

    TileType(Image image) {
        this.image = image;
    }

    public void render(GraphicsContext gc, double x, double y, double w, double h) {
        gc.drawImage(image, x, y, w, h);
    }

    public boolean blockTile() {
        return this == Wall
                || this == Brick;
    }
}
