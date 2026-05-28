package com.isep.ead.controllers.widgets.inputs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.util.List;

public class FormComboController extends FormInputController{
    @FXML
    private Label inputName;

    @FXML
    private ComboBox<String> comboBox;

    @Override
    public String getValue() {
        return this.comboBox.getValue();
    }

    @Override
    public void setText(String inputName) {
        this.inputName.setText(inputName);
    }

    public void setItems(List<String> items) {
        this.comboBox.setItems(FXCollections.observableArrayList(items));
    }

    @Override
    public void setDefaultValue(String value) {
        this.comboBox.setValue(value);
    }
}
