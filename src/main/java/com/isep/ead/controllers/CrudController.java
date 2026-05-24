package com.isep.ead.controllers;

import com.isep.ead.models.organization.Organization;
import com.isep.ead.widgets.FormScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class CrudController extends Controller implements ICrudController {

    @FXML
    private Button createButton;

    @FXML
    private Button modifyButton;

    @FXML
    private Button deleteButton;

    private final Map<Button, Runnable> buttonAction = new HashMap<>();

    @FXML
    protected void initialize() {
        this.buttonAction.put(createButton, this::add);
        this.buttonAction.put(modifyButton, this::modify);
        this.buttonAction.put(deleteButton, this::delete);
    }

    @Override
    public void index() {

    }

    @Override
    public void add() {
    }

    @Override
    public void modify() {

    }

    @Override
    public void delete() {

    }
    @FXML
    private void onClick(ActionEvent event) {
        Button source = (Button) event.getSource();
        Runnable action = this.buttonAction.get(source);
        if(action != null) {
            action.run();
        }
    }
}
