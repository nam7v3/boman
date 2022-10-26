package com.github.boman;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MenuController {
    @FXML
    public Button play;
    @FXML
    public Button quit;
    @FXML
    public VBox vBox;

    @FXML
    public void startGame() {
        SceneManager.startNewGame();
    }

    @FXML
    public void quitGame() {
        System.exit(0);
    }
    public void resizeWidth(double v) {
        vBox.setMinWidth(v);
        vBox.setPrefWidth(v);
        vBox.setMaxWidth(v);
    }

    public void resizeHeight(double v) {
        vBox.setMinHeight(v);
        vBox.setPrefHeight(v);
        vBox.setMaxHeight(v);
    }
}
