package com.github.boman;

import com.github.boman.event.EventHandlerListener;
import com.github.boman.game.BomanEngine;
import com.github.boman.game.BomanRenderer;
import com.github.boman.game.GameLoop;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private EventHandlerListener eventHandlerListener;
    private BomanEngine engine;
    public BomanRenderer renderer;
    @FXML
    public Canvas canvas;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.eventHandlerListener = new EventHandlerListener();
        this.engine = new BomanEngine(eventHandlerListener);
        this.renderer = new BomanRenderer(this.canvas);

        engine.loadMap("test.txt");

        GameLoop loop = new GameLoop() {
            @Override
            public void tick() {
                engine.update();
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
