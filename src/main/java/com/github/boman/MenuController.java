package com.github.boman;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuController {
    @FXML
    public Button play;
    @FXML
    public Button quit;

    @FXML
    public void startGame() {
        SceneManager.loadGame();
    }

    @FXML
    public void quitGame() {
        System.exit(0);
    }
}
