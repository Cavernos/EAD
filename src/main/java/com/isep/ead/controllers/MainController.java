package com.isep.ead.controllers;

import com.isep.ead.controllers.crud.OrganizationCrudController;
import com.isep.ead.controllers.widgets.ButtonClickController;
import com.isep.ead.utils.LoadedView;
import javafx.fxml.FXML;
import javafx.scene.Parent;
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
    @FXML
    private Button historiqueButton;

    private Parent currentScene;


    @FXML
    public void initialize() {
        this.buttonAction.put(this.organizationButton, this::organization);
        this.buttonAction.put(this.energyButton, this::energy);
        this.buttonAction.put(this.dashboardButton, this::dashboard);
        this.buttonAction.put(this.historiqueButton, this::historique);
    }

    public void organization() {
        ((OrganizationCrudController)this.switchView("organisation-view")).index();
    }

    public void dashboard() {
        DashboardController c = (DashboardController) this.switchView("tableau-de-bord-view");
        c.refresh();
    }

    public void energy() {
        this.switchView("consomation-graphique");
    }

    public void historique() {
        this.switchView("historique-consomation-view");
    }

    public Controller switchView(String view) {
        LoadedView scene = this.sceneManager.loadPage(view);
        if(!scene.getRoot().equals(this.currentScene)) {
            this.contentDash.getChildren().clear();
            this.currentScene = scene.getRoot();
            this.contentDash.getChildren().add(this.currentScene);
        }
        return scene.getController();

    }
}
