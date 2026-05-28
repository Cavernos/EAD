package com.isep.ead.controllers.widgets.inputs;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.util.List;
import java.util.function.Consumer;

public class FormComboController extends FormInputController{
    @FXML
    private Label inputName;

    @FXML
    private ComboBox<String> comboBox;

    @Override
    public String getValue() {
        return this.comboBox.getValue();
    }

    /** Un ComboBox est valide si une valeur non vide est sélectionnée. */
    @Override
    public boolean isValid() {
        String val = this.comboBox.getValue();
        return val != null && !val.trim().isEmpty();
    }

    /** Pour les ComboBox, validateAndShow met en rouge si rien n'est sélectionné. */
    @Override
    public boolean validateAndShow() {
        boolean valid = isValid();
        String base = "-fx-background-color: #f8f9fa; -fx-border-radius: 5;";
        this.comboBox.setStyle(valid
            ? base + " -fx-border-color: #27ae60; -fx-border-width: 2;"
            : base + " -fx-border-color: #e74c3c; -fx-border-width: 2;");
        return valid;
    }

    @Override
    public void setText(String inputName) {
        this.inputName.setText(inputName);
    }

    public void setItems(String[] items) {
        this.comboBox.setItems(FXCollections.observableArrayList(items));
    }

    public void setOnValueChange(Consumer<String> listener) {
        this.comboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) listener.accept(newVal);
        });
    }

    @Override
    public void setDefaultValue(String value) {
        this.comboBox.setValue(value);
    }
}
