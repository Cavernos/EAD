package com.isep.ead.controllers.widgets.popup;



import com.isep.ead.controllers.widgets.inputs.FormInputController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
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

    public void addField(String name, String labelText) {
        Parent field = this.sceneManager.loadScene("components/input/FormInput");
        FormInputController inputController = (FormInputController) this.sceneManager.getSceneController();
        inputController.setText(labelText);
        this.contentBox.getChildren().add(field);
        this.fields.put(name, inputController);
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
