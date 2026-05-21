module com.isep.ead {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.isep.ead to javafx.fxml;
    exports com.isep.ead;
}