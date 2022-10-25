package com.github.boman;

import com.github.boman.event.EventHandlerListener;
import com.github.boman.game.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    private EventHandlerListener eventHandlerListener;
    private BomanEngine engine;
    public BomanRenderer renderer;
    public HUD hud;
    @FXML
    public VBox gameScene;
    @FXML
    public Canvas canvas;
    @FXML
    public HBox menu;

    public enum GameState {
        Win,
        Playing,
        Lose,
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.eventHandlerListener = new EventHandlerListener();
        this.engine = new BomanEngine(eventHandlerListener);
        this.renderer = new BomanRenderer(this.canvas);


        menu.prefWidthProperty().bind(gameScene.widthProperty());
        menu.minWidthProperty().bind(gameScene.widthProperty());
        menu.maxWidthProperty().bind(gameScene.widthProperty());


        engine.loadMap("level1.txt");

        this.hud = new HUD(engine, menu);
        gameScene.setFocusTraversable(true);
        gameScene.addEventHandler(Event.ANY, this::onEvent);

        GameLoop loop = new GameLoop() {
            @Override
            public void tick() {
                engine.update();
                if (engine.winGame()) {
                    SceneManager.loadMainMenu();
                    stop();
                    return;
                }
                hud.update();
                renderer.render(engine);
            }
        };
        loop.start();
    }

    public static void which(Engine engine) {
        if (engine.winGame()) {
            SceneManager.loadMainMenu();
        }
    }

    @FXML
    protected void onEvent(Event event) {
        this.eventHandlerListener.handle(event);
    }
}
