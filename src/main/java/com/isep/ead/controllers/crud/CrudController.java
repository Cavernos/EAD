package com.isep.ead.controllers.crud;

import com.isep.ead.controllers.Controller;
import com.isep.ead.controllers.widgets.ButtonClickController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.HashMap;
import java.util.Map;

public class CrudController extends ButtonClickController implements ICrudController {

    @FXML
    private Button createButton;

    @FXML
    private Button modifyButton;

    @FXML
    private Button deleteButton;

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

}
