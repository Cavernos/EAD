package com.isep.ead.widgets.popup;

import com.isep.ead.controllers.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    public Label contentLabel;

    @FXML
    private void onCancel() {
        ((Stage)this.contentBox.getScene().getWindow()).close();
    }

    @FXML
    private void onSave() {
        this.onCancel();
    }
}
