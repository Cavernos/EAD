package com.isep.ead.controllers.crud;

import com.isep.ead.controllers.widgets.ButtonClickController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CrudController extends ButtonClickController implements ICrudController {

    @FXML
    private Button createButton;

    @FXML
    protected Button modifyButton;

    @FXML
    protected Button deleteButton;

    @FXML
    protected void initialize() {
        this.buttonAction.put(createButton, this::add);
    }

    @Override
    public void index() {

    }

    @Override
    public void add() {
    }

    @Override
    public void modify(int id) {

    }

    @Override
    public void delete(int id) {

    }

}
