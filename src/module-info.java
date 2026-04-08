module MCO1 {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    exports views;
    opens views to javafx.graphics, javafx.fxml;
}