package com.github.boman;

import com.github.boman.entity.Bomber;
import com.github.boman.entity.Grass;
import com.github.boman.entity.Wall;
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
        Bomber player = new Bomber(Wall.WIDTH + (Wall.WIDTH - Bomber.BOMBER_WIDTH) / 2,
                                Wall.HEIGHT + (Wall.HEIGHT - Bomber.BOMBER_HEIGHT) / 2);
        eventHandlerListener.addListener(player);
        this.renderer = new BomanRenderer(this.canvas);

        for (int i = 0; i <= 30; i++) {
            for (int j = 0; j <= 12; j++) {
                if (i == 0 || j == 0 || i == 30 || j == 12 || (i % 2 == 0 && j % 2 == 0)) {
                    Wall wall = new Wall(i * 20, j * 20);
                    engine.add(wall);
                } else {
                    Grass grass = new Grass(i * 20, j * 20);
                    engine.add(grass);
                }
            }
        }
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
