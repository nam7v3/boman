package com.github.boman;

import com.github.boman.entity.Bomber;
import com.github.boman.event.EventHandlerListener;
import com.github.boman.game.BomanEngine;
import com.github.boman.game.BomanRenderer;
import com.github.boman.game.GameLoop;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;

import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private EventHandlerListener eventHandlerListener;
    private BomanEngine engine;
    private BomanRenderer renderer;
    @FXML
    private Canvas canvas;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.eventHandlerListener = new EventHandlerListener();
        this.engine = new BomanEngine();
        this.renderer = new BomanRenderer(this.canvas);
        Bomber player = new Bomber(10, 10);
        eventHandlerListener.addListener(player);
        engine.add(player);

        GameLoop loop = new GameLoop() {
            @Override
            public void tick(Duration elapsed) {
                engine.update(elapsed);
                renderer.render(engine.getEntities());
            }
        };
        loop.start();
    }

    @FXML
    protected void onEvent(Event event) {
        this.eventHandlerListener.handle(event);
    }
}
