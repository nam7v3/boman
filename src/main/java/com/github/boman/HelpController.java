package com.github.boman;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class HelpController implements Initializable {
    @FXML
    public Label wLabel;
    @FXML
    public Label aLabel;
    @FXML
    public Label sLabel;
    @FXML
    public Label dLabel;
    @FXML
    public Label spaceLabel;
    @FXML
    public Button returnButton;
    @FXML
    public StackPane root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Font font = Font.loadFont(getClass().getResource("pixeloidMono.ttf").toString(), 30);
        wLabel.setFont(font);
        aLabel.setFont(font);
        sLabel.setFont(font);
        dLabel.setFont(font);
        spaceLabel.setFont(font);
        Font buttonFont = Font.loadFont(getClass().getResource("pixeloidMono.ttf").toString(), 18);
        returnButton.setFont(buttonFont);
    }

    public void resizeWidth(double v) {
        root.setMinWidth(v);
        root.setPrefWidth(v);
        root.setMaxWidth(v);
    }

    public void resizeHeight(double v) {
        root.setMinHeight(v);
        root.setPrefHeight(v);
        root.setMaxHeight(v);
    }

    public void returnMenu(){
        SceneManager.loadMainMenu();
    }

}
