package com.github.boman;

import com.github.boman.util.SoundEffects;
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
                FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("menu.fxml"));
                Node node = loader.load();
                MenuController menuController = loader.getController();
                mainController.setScene(node);
                mainController.mainRoot.widthProperty().addListener(
                        ((observableValue, number, t1) -> menuController.resizeWidth((double) t1))
                );
                mainController.mainRoot.heightProperty().addListener(
                        ((observableValue, number, t1) -> menuController.resizeHeight((double) t1))
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mainController.setScene(scenes.get("menu"));
        }
        SoundEffects.instance.playSound(SoundEffects.SoundIndex.MAIN_MENU);
    }

    public static void startNewGame(){
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("game.fxml"));
            Node node = loader.load();
            GameController gameController = loader.getController();
            mainController.mainRoot.widthProperty().addListener(
                    ((observableValue, number, t1) -> gameController.renderer.resizeWidth((Double) t1))
            );
            mainController.mainRoot.heightProperty().addListener(
                    ((observableValue, number, t1) -> gameController.renderer.resizeHeight((Double) t1))
            );
            mainController.setScene(node);
            scenes.put("game", node);
            SoundEffects.instance.stopAllSound();
            SoundEffects.instance.playSound(SoundEffects.SoundIndex.BGM);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadWinScreen() {
        if (scenes.get("win") == null) {
            try {
                FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("win.fxml"));
                Node node = loader.load();
                WinLoseController winController = loader.getController();
                mainController.setScene(node);
                mainController.mainRoot.widthProperty().addListener(
                        ((observableValue, number, t1) -> winController.resizeWidth((double) t1))
                );
                mainController.mainRoot.heightProperty().addListener(
                        ((observableValue, number, t1) -> winController.resizeHeight((double) t1))
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mainController.setScene(scenes.get("win"));
        }
        SoundEffects.instance.stopAllSound();
        SoundEffects.instance.playSound(SoundEffects.SoundIndex.WIN);
    }

    public static void loadLoseScreen() {
        if (scenes.get("lose") == null) {
            try {
                FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("lose.fxml"));
                Node node = loader.load();
                WinLoseController loseController = loader.getController();
                mainController.setScene(node);
                mainController.mainRoot.widthProperty().addListener(
                        ((observableValue, number, t1) -> loseController.resizeWidth((double) t1))
                );
                mainController.mainRoot.heightProperty().addListener(
                        ((observableValue, number, t1) -> loseController.resizeHeight((double) t1))
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mainController.setScene(scenes.get("lose"));
        }
        SoundEffects.instance.stopAllSound();
        SoundEffects.instance.playSound(SoundEffects.SoundIndex.GAME_OVER);
    }

    public static void loadGame() {
        if (scenes.get("game") == null) {
            startNewGame();
        } else {
            mainController.setScene(scenes.get("game"));
        }
    }
}
