package com.github.boman;

import com.github.boman.event.EventHandlerListener;
import com.github.boman.game.BomanEngine;
import com.github.boman.game.BomanRenderer;
import com.github.boman.game.GameLoop;
import com.github.boman.game.HUD;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private EventHandlerListener eventHandlerListener;
    private BomanEngine engine;
    public BomanRenderer renderer;

    public HUD hud;
    @FXML
    public Canvas canvas;
    @FXML
    public HBox menu;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.eventHandlerListener = new EventHandlerListener();
        this.engine = new BomanEngine(eventHandlerListener);
        this.renderer = new BomanRenderer(this.canvas);


        engine.loadMap("level1.txt");
        this.hud = new HUD(engine, menu);

        GameLoop loop = new GameLoop() {
            @Override
            public void tick() {
                engine.update();
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
}
