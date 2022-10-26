package com.github.boman;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class WinLoseController {
    @FXML
    public StackPane stackPane;

    @FXML
    public void returnToMainmenu() {
        SceneManager.loadMainMenu();
    }

    @FXML
    public void quitGame() {
        System.exit(0);
    }

    public void resizeWidth(double v) {
        stackPane.setMinWidth(v);
        stackPane.setPrefWidth(v);
        stackPane.setMaxWidth(v);
    }

    public void resizeHeight(double v) {
        stackPane.setMinHeight(v);
        stackPane.setPrefHeight(v);
        stackPane.setMaxHeight(v);
    }
}
