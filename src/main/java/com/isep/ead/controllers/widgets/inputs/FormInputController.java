package com.isep.ead.controllers.widgets.inputs;

import com.isep.ead.controllers.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FormInputController extends Controller {
    @FXML
    private Label inputName;

    @FXML
    private TextField inputField;

    public String getValue() {
        return this.inputField.getText();
    }

    public void setText(String inputName) {
        this.inputName.setText(inputName);
    }
}
