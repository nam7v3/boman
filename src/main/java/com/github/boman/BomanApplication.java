package com.github.boman;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class BomanApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        StackPane root = loader.load();
        SceneManager.setMainController(loader.getController());
        SceneManager.loadMainMenu();
        Scene scene = new Scene(root, 750, 500);

        root.prefHeightProperty().bind(scene.heightProperty());
        root.prefWidthProperty().bind(scene.widthProperty());

        stage.setTitle("Boman");
        stage.setScene(scene);

        root.prefHeightProperty().bind(stage.heightProperty());
        root.prefWidthProperty().bind(stage.widthProperty());

        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
