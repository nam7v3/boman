package com.github.boman;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class MainController {
    @FXML
    public StackPane mainRoot;

    public void setScene(Node node){
        mainRoot.getChildren().clear();
        mainRoot.getChildren().add(node);
    }
}
