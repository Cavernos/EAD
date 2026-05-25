package com.isep.ead.controllers.widgets.popup;

import com.isep.ead.controllers.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PopupController extends Controller {

    @FXML
    private VBox contentBox;

    @FXML
    private Button cancelButton;
    @FXML
    protected Button submitButton;

    @FXML
    private void onCancel() {
        ((Stage)this.contentBox.getScene().getWindow()).close();
    }

    @FXML
    private void onSave() {
        this.onCancel();
    }
}
