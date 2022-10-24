package com.github.boman.game;

import com.github.boman.sprites.Animation;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class HUD {
    private Engine engine;
    private HBox hud;

    private Label lives;
    private ImageView player;
    private ImageView bomb;
    private Label bombLabel;
    private Animation bombAnimation = Animation.getBombAnimation();

    public HUD(Engine engine, HBox hud) {
        Font font = Font.loadFont(getClass().getResource("pixeloidMono.ttf").toString(), 20);
        this.engine = engine;
        this.hud = hud;
        player = new ImageView();
        player.fitHeightProperty().bind(hud.heightProperty());
        player.setPreserveRatio(true);
        player.setSmooth(false);

        lives = new Label();
        lives.setText(":" + engine.getPlayer().getLives());
        lives.prefHeightProperty().bind(hud.heightProperty());
        lives.setTextFill(Color.WHITE);
        lives.setFont(font);

        bomb = new ImageView();
        bomb.fitHeightProperty().bind(hud.heightProperty());
        bomb.setPreserveRatio(true);
        bomb.setSmooth(false);

        bombLabel = new Label();
        bombLabel.setText(":" + engine.getPlayer().getMaxBomb());
        bombLabel.prefHeightProperty().bind(hud.heightProperty());
        bombLabel.setTextFill(Color.WHITE);
        bombLabel.setFont(font);

        hud.setBackground(Background.fill(Color.BLACK));
        hud.setPrefHeight(100);
        hud.setMinHeight(30);
        hud.setPadding(new Insets(5, 5, 5, 5));
        hud.setSpacing(10);
        hud.getChildren().add(player);
        hud.getChildren().add(lives);
        hud.getChildren().add(bomb);
        hud.getChildren().add(bombLabel);
    }

    public void update() {
        player.setImage(
                engine.getPlayer().getAnimation().getCurImage()
        );
        lives.setText(":" + engine.getPlayer().getLives());
        bombLabel.setText(":" + engine.getPlayer().getMaxBomb());
        bomb.setImage(bombAnimation.getImage());
    }
}
