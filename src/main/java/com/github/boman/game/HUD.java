package com.github.boman.game;

import com.github.boman.sprites.Animation;
import com.github.boman.sprites.Sprite;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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

    private Label onealLabel;

    private Label balloomLabel;

    public HUD(Engine engine, HBox hud) {
        Font font = Font.loadFont(getClass().getResource("pixeloidMono.ttf").toString(), 20);
        this.engine = engine;
        this.hud = hud;

        Button pauseButton = new Button();
        pauseButton.setText("Pause");
        pauseButton.setFocusTraversable(false);
        pauseButton.setOnMousePressed(actionEvent -> {
            engine.togglePause();
        });

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

        ImageView balloom = new ImageView();
        balloom.setImage(Sprite.balloomLeft1);
        balloom.setPreserveRatio(true);
        balloom.setSmooth(false);

        balloomLabel = new Label();
        balloomLabel.setTextFill(Color.WHITE);
        balloomLabel.setFont(font);

        ImageView oneal = new ImageView();
        oneal.setImage(Sprite.onealLeft1);
        oneal.setPreserveRatio(true);
        oneal.setSmooth(false);

        onealLabel = new Label();
        onealLabel.setFont(font);
        onealLabel.setTextFill(Color.WHITE);

        hud.setAlignment(Pos.CENTER_LEFT);
        hud.setBackground(Background.fill(Color.GREEN));
        hud.setScaleShape(true);
        hud.setFillHeight(true);

        hud.getChildren().addAll(pauseButton, player, lives, bomb, bombLabel, balloom, balloomLabel, oneal, onealLabel);

    }

    public void update() {
        player.setImage(
                engine.getPlayer().getAnimation().getCurImage()
        );
        lives.setText(":" + engine.getPlayer().getLives());
        bombLabel.setText(":" + engine.getPlayer().getMaxBomb());
        balloomLabel.setText(":" + engine.getBalloomCount());
        onealLabel.setText(":" + engine.getOnealCount());
        bomb.setImage(bombAnimation.getImage());
    }

    public void resizeWidth(double t) {
        hud.setMinWidth(t);
    }
}
