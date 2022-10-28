package com.github.boman;

import com.github.boman.event.EventHandlerListener;
import com.github.boman.game.BomanEngine;
import com.github.boman.game.BomanRenderer;
import com.github.boman.game.Duration;
import com.github.boman.game.GameLoop;
import com.github.boman.sprites.Animation;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    private EventHandlerListener eventHandlerListener;
    private BomanEngine engine;
    public BomanRenderer renderer;
    public Duration delay = Duration.of(120);
    @FXML
    public Canvas canvas;
    @FXML
    public HBox menu;
    @FXML
    public BorderPane gameScene;
    @FXML
    public Button pauseButton;
    @FXML
    public ImageView playerImage;
    @FXML
    public Label liveCount;
    @FXML
    public ImageView bombImage;
    @FXML
    public Label bombCount;
    @FXML
    public ImageView onealImage;
    @FXML
    public Label onealCount;
    @FXML
    public ImageView balloomImage;
    @FXML
    public Label balloomCount;
    @FXML
    public ImageView kondoriaImage;
    @FXML
    public Label kondoriaCount;
    @FXML
    public ImageView minvoImage;
    @FXML
    public Label minvoCount;
    @FXML
    public ImageView dollImage;
    @FXML
    public Label dollCount;
    @FXML
    public ImageView thwimpImage;
    @FXML
    public Label thwimpCount;

    private Animation playerAnimation = Animation.getPlayerAnimation();
    private Animation bombAnimation = Animation.getBombAnimation();
    private Animation onealAnimation = Animation.getOnealAnimation();
    private Animation balloomAnimation = Animation.getBalloomAnimation();
    private Animation kondoriaAnimation = Animation.getKondoriaAnimation();
    private Animation minvoAnimation = Animation.getMinvoAnimation();
    private Animation dollAnimation = Animation.getDollAnimation();
    private Animation thwimpAnimation = Animation.getThwimpAnimation();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.eventHandlerListener = new EventHandlerListener();
        this.engine = new BomanEngine(eventHandlerListener);
        this.renderer = new BomanRenderer(this.canvas);


        menu.prefWidthProperty().bind(gameScene.widthProperty());
        menu.minWidthProperty().bind(gameScene.widthProperty());
        menu.maxWidthProperty().bind(gameScene.widthProperty());

        engine.nextLevel();

        Font font = Font.loadFont(GameController.class.getResource("pixeloidMono.ttf").toString(), 13);
        pauseButton.setFont(font);
        liveCount.setFont(font);
        balloomCount.setFont(font);
        bombCount.setFont(font);
        onealCount.setFont(font);
        kondoriaCount.setFont(font);
        minvoCount.setFont(font);
        dollCount.setFont(font);
        thwimpCount.setFont(font);

        balloomImage.setSmooth(false);
        playerImage.setSmooth(false);
        bombImage.setSmooth(false);
        onealImage.setSmooth(false);
        kondoriaImage.setSmooth(false);
        minvoImage.setSmooth(false);
        dollImage.setSmooth(false);
        thwimpImage.setSmooth(false);

        gameScene.setFocusTraversable(true);
        gameScene.addEventHandler(Event.ANY, this::onEvent);
        gameScene.setMaxWidth(Double.MAX_VALUE);
        gameScene.setMaxHeight(Double.MAX_VALUE);

        GameLoop loop = new GameLoop() {
            @Override
            public void tick() {
                engine.update();
                if (engine.winGame()) {
                    SceneManager.loadWinScreen();
                    stop();
                    return;
                }
                if (engine.loseGame()) {
                    if (delay.isNegative()) {
                        SceneManager.loadLoseScreen();
                        stop();
                        return;
                    } else {
                        delay.minus();
                    }
                }
                hudUpdate();
                renderer.render(engine);
            }
        };
        loop.start();
    }

    public void pauseGame() {
        engine.togglePause();
    }

    public void hudUpdate() {
        onealCount.setText(":" + engine.getOnealCount());
        balloomCount.setText(":" + engine.getOnealCount());
        bombCount.setText(":" + engine.getPlayer().getMaxBomb());
        liveCount.setText(":" + engine.getPlayer().getLives());
        kondoriaCount.setText(":" + engine.getKondoriaCount());
        minvoCount.setText(":" + engine.getMinvoCount());
        dollCount.setText(":" + engine.getDollCount());
        thwimpCount.setText(":" + engine.getThwimpCount());

        playerImage.setImage(engine.getPlayer().getAnimation().getCurImage());
        bombImage.setImage(bombAnimation.getImage());
        balloomImage.setImage(balloomAnimation.getImage());
        onealImage.setImage(onealAnimation.getImage());
        kondoriaImage.setImage(kondoriaAnimation.getImage());
        minvoImage.setImage(minvoAnimation.getImage());
        dollImage.setImage(dollAnimation.getImage());
        thwimpImage.setImage(thwimpAnimation.getImage());
    }

    @FXML
    protected void onEvent(Event event) {
        this.eventHandlerListener.handle(event);
    }

    public void resizeWidth(double w) {
        renderer.resizeWidth(w);
    }

    public void resizeHeight(double h) {
        renderer.resizeHeight(h);
    }

}
