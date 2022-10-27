package com.github.boman;

import com.github.boman.event.EventHandlerListener;
import com.github.boman.game.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    private EventHandlerListener eventHandlerListener;
    private BomanEngine engine;
    public BomanRenderer renderer;
    public HUD hud;
    public Duration delay = Duration.of(120);
    @FXML
    public Canvas canvas;
    @FXML
    public HBox menu;

    @FXML
    public BorderPane gameScene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.eventHandlerListener = new EventHandlerListener();
        this.engine = new BomanEngine(eventHandlerListener);
        this.renderer = new BomanRenderer(this.canvas);


        menu.prefWidthProperty().bind(gameScene.widthProperty());
        menu.minWidthProperty().bind(gameScene.widthProperty());
        menu.maxWidthProperty().bind(gameScene.widthProperty());

        engine.nextLevel();

        this.hud = new HUD(engine, menu);
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
                hud.update();
                renderer.render(engine);
            }
        };
        loop.start();
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
