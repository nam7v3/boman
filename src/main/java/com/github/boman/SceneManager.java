package com.github.boman;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneManager {
    public static final String menu = "menu.fxml";
    public static final String game = "game.fxml";

    public static final Map<String, Node> scenes = new HashMap<>();

    public static MainController mainController;

    public static void setMainController(MainController mainController) {
        SceneManager.mainController = mainController;
    }

    public static void loadMainMenu() {
        if (scenes.get("menu") == null) {
            try {
                Node node = FXMLLoader.load(SceneManager.class.getResource("menu.fxml"));
                scenes.put("menu", node);
                mainController.setScene(node);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mainController.setScene(scenes.get("menu"));
        }
    }

    public static void loadGame() {
        if (scenes.get("game") == null) {
            try {
                FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("game.fxml"));
                Node node = loader.load();
                GameController gameController = loader.getController();
                mainController.mainRoot.widthProperty().addListener(
                        ((observableValue, number, t1) -> {
                            gameController.renderer.resizeWidth((Double) t1);
                        })
                );
                mainController.mainRoot.heightProperty().addListener(
                        ((observableValue, number, t1) -> {
                            gameController.renderer.resizeHeight((Double) t1);
                        })
                );
                mainController.setScene(node);
                scenes.put("game", node);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mainController.setScene(scenes.get("game"));
        }
    }
}