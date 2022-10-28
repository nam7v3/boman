package com.github.boman;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class WinLoseController implements Initializable {
    @FXML
    public StackPane stackPane;

    @FXML
    public Button returnButton;

    @FXML
    public Button quitButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Font font = Font.loadFont(GameController.class.getResource("pixeloidMono.ttf").toString(), 18);
        returnButton.setFont(font);
        quitButton.setFont(font);
    }

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
