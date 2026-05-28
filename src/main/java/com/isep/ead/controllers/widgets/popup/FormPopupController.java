package com.isep.ead.controllers.widgets.popup;


import com.isep.ead.controllers.Controller;
import com.isep.ead.controllers.widgets.inputs.FormComboController;
import com.isep.ead.controllers.widgets.inputs.FormInputController;
import com.isep.ead.models.building.Building;
import com.isep.ead.utils.LoadedView;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FormPopupController extends PopupController {
    private final Map<String, FormInputController> fields = new HashMap<>();
    private Runnable onSubmitAction;
    @FXML
    public Label nameLabel;
    @FXML
    private VBox contentBox;

    private FormInputController addField(String view, String name, String labelText, String defaultValue){
        LoadedView field = this.sceneManager.loadTemplate(view);
        FormInputController inputController = (FormInputController) field.getController();
        inputController.setText(labelText);
        inputController.setDefaultValue(defaultValue);
        this.contentBox.getChildren().add(field.getRoot());
        this.fields.put(name, inputController);
        return inputController;
    }

    public void addField(String name, String labelText, String defaultValue) {
        this.addField("components/input/FormInput", name, labelText, defaultValue);
    }
    public void addField(String name, String labelText) {
        this.addField(name, labelText, "");
    }

    public void addComboField(String name, String labelText, ArrayList<String> items, String defaultValue) {
        ((FormComboController)this.addField("components/input/FormCombo", name, labelText, defaultValue)).setItems(items);

    }
    public void addComboField(String name, String labelText, ArrayList<String> items) {
        ((FormComboController)this.addField("components/input/FormCombo", name, labelText, "")).setItems(items);

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
