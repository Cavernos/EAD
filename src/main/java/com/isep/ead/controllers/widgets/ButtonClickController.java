package com.isep.ead.controllers.widgets;

import com.isep.ead.controllers.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.HashMap;
import java.util.Map;

public abstract class ButtonClickController extends Controller {
    protected final Map<Button, Runnable> buttonAction = new HashMap<>();
    @FXML
    private void onClick(ActionEvent event) {
        Button source = (Button) event.getSource();
        Runnable action = this.buttonAction.get(source);
        if(action != null) {
            action.run();
        }
    }
}
