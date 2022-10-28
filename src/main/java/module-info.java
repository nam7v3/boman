module com.github.boman {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.github.boman to javafx.fxml;
    exports com.github.boman;
}