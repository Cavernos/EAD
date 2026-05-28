package com.isep.ead.widgets.popup;

import com.isep.ead.controllers.Controller;
import com.isep.ead.controllers.widgets.popup.FormPopupController;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class Popup {
    private final Stage stage;
    private final Parent root;
    private final Controller controller;

    public Popup(Stage stage, Parent root, Controller controller) {
        this.stage = stage;
        this.root = root;
        this.controller = controller;
    }

    public Controller getController() {
        return this.controller;
    }
    public void setTitle(String title) {
        this.stage.setTitle(title);
    }
    public void show() {
        this.stage.show();
    }

    public void onSubmit(Runnable action) {
        if (this.controller instanceof FormPopupController controller) {
            controller.setOnSubmitAction(() -> {
                action.run();
                this.stage.close();
            });
        }
    }
}
