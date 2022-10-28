package com.github.boman;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    @FXML
    public Button play;
    @FXML
    public Button quit;
    @FXML
    public Button help;
    @FXML
    public VBox vBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Font font = Font.loadFont(GameController.class.getResource("pixeloidMono.ttf").toString(), 18);
        play.setFont(font);
        help.setFont(font);
        quit.setFont(font);
    }

    @FXML
    public void startGame() {
        SceneManager.startNewGame();
    }

    @FXML
    public void quitGame() {
        System.exit(0);
    }

    @FXML
    public void helpScene(){
        SceneManager.loadHelpScreen();
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
