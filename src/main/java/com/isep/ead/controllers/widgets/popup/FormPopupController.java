package com.isep.ead.controllers.widgets.popup;


import com.isep.ead.controllers.widgets.inputs.FormInputController;
import com.isep.ead.utils.LoadedView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class FormPopupController extends PopupController {
    private final Map<String, FormInputController> fields = new HashMap<>();
    private Runnable onSubmitAction;
    @FXML
    public Label nameLabel;
    @FXML
    private VBox contentBox;

    public void addField(String name, String labelText, String defaultValue) {
        LoadedView field = this.sceneManager.loadTemplate("components/input/FormInput");
        FormInputController inputController = (FormInputController) field.getController();
        inputController.setText(labelText);
        inputController.setDefaultValue(defaultValue);
        this.contentBox.getChildren().add(field.getRoot());
        this.fields.put(name, inputController);
    }

    public void addField(String name, String labelText) {
        this.addField(name, labelText, "");
    }

    public void setPopupName(String name) {
        this.nameLabel.setText(name);
    }

    public String getValues(String name) {
        if (this.fields.containsKey(name)){
            return this.fields.get(name).getValue();
        }
        return "";
    }

    public void setOnSubmitAction(Runnable action) {
        this.onSubmitAction = action;
    }
    @FXML
    private void onFormSave() {
        if(this.onSubmitAction != null) {
            this.onSubmitAction.run();
        }

    }

}
