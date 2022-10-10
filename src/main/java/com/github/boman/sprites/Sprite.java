package com.github.boman.sprites;

import javafx.scene.image.Image;

public class Sprite {
    public static Image wall = new Image(Sprite.class.getResource("wall.png").toString());
    public static Image grass = new Image(Sprite.class.getResource("grass.png").toString());
    public static Image bomberUp = new Image(Sprite.class.getResource("bomberUp.png").toString());
    public static Image bomberDown = new Image(Sprite.class.getResource("bomberDown.png").toString());
    public static Image bomberLeft = new Image(Sprite.class.getResource("bomberLeft.png").toString());
    public static Image bomberRight = new Image(Sprite.class.getResource("bomberRight.png").toString());
    public static Image bomb = new Image(Sprite.class.getResource("bomb.png").toString());
    public static Image brick = new Image(Sprite.class.getResource("brick.png").toString());
}
