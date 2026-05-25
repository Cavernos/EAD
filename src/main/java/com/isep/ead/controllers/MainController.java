package com.isep.ead.controllers;

import com.isep.ead.controllers.widgets.ButtonClickController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MainController extends ButtonClickController {

    @FXML
    private VBox contentDash;
    @FXML
    private Button energyButton;
    @FXML
    private Button organizationButton;
    @FXML
    private Button dashboardButton;

    private Parent currentScene;


    @FXML
    public void initialize() {
        this.buttonAction.put(this.organizationButton, this::organization);
        this.buttonAction.put(this.energyButton, this::energy);
        this.buttonAction.put(this.dashboardButton, this::dashboard);
    }

    public void organization() {
        this.switchView("organisation-view");
    }

    public void dashboard() {

    }

    public void energy() {
        this.switchView("organisation-form-view");

    }

    public void switchView(String view) {
        Parent scene = this.sceneManager.loadScene(view);
        if(!scene.equals(this.currentScene)) {
            this.contentDash.getChildren().clear();
            this.currentScene = scene;
            this.contentDash.getChildren().add(this.currentScene);
            this.sceneManager.getSceneController();

        }

    }
}
