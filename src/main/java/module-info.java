module com.github.boman {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.github.boman to javafx.fxml;
    exports com.github.boman;
}