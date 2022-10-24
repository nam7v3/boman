package com.github.boman.game;

import com.github.boman.sprites.Animation;
import javafx.geometry.Pos;
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
        player.setPreserveRatio(true);
        player.setSmooth(false);

        lives = new Label();
        lives.setText(":" + engine.getPlayer().getLives());
        lives.setTextFill(Color.WHITE);
        lives.setFont(font);


        bomb = new ImageView();
        bomb.setPreserveRatio(true);
        bomb.setSmooth(false);


        bombLabel = new Label();
        bombLabel.setText(":" + engine.getPlayer().getMaxBomb());
        bombLabel.setTextFill(Color.WHITE);
        bombLabel.setFont(font);


        hud.setAlignment(Pos.CENTER_LEFT);
        hud.setBackground(Background.fill(Color.GRAY));
        hud.setScaleShape(true);
        hud.setFillHeight(true);

        hud.getChildren().addAll(player, lives, bomb, bombLabel);

    }

    public void update() {
        player.setImage(
                engine.getPlayer().getAnimation().getCurImage()
        );
        lives.setText(":" + engine.getPlayer().getLives());
        bombLabel.setText(":" + engine.getPlayer().getMaxBomb());
        bomb.setImage(bombAnimation.getImage());
    }

    public void resizeWidth(double t) {
        hud.setMinWidth(t);
    }
}
