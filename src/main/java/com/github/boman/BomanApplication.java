package com.github.boman;

import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BomanApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
        Scene scene = new Scene(loader.load(), 750, 500);
        Controller controller = loader.getController();
        scene.addEventHandler(Event.ANY, controller::onEvent);
        stage.setScene(scene);
        stage.widthProperty().addListener((observableValue, number, t1) -> controller.renderer.resizeWidth((Double) t1));
        stage.heightProperty().addListener((observableValue, number, t1) -> controller.renderer.resizeHeight((Double) t1));
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
