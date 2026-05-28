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
    @FXML
    private Button comparaisonButton;

    private Parent currentScene;


    @FXML
    public void initialize() {
        this.buttonAction.put(this.organizationButton, this::organization);
        this.buttonAction.put(this.energyButton, this::energy);
        this.buttonAction.put(this.dashboardButton, this::dashboard);
        this.buttonAction.put(this.historiqueButton, this::historique);
        this.buttonAction.put(this.comparaisonButton, this::comparaison);
    }

    public void organization() {
        ((OrganizationCrudController)this.switchView("organisation-view")).index();
    }

    public void dashboard() {
        DashboardController c = (DashboardController) this.switchView("tableau-de-bord-view");
        c.refresh();
    }

    public void energy() {
        var lv = this.switchViewRaw("consomation-graphique");
        if (lv.getController() instanceof ConsomationController c) c.refreshCombos();
    }

    public void historique() {
        var lv = this.switchViewRaw("historique-consomation-view");
        if (lv.getController() instanceof ConsomationController c) c.refreshCombos();
    }

    public void comparaison() {
        var lv = this.switchViewRaw("comparaison-batiments-view");
        if (lv.getController() instanceof ComparaisonController c) c.refreshCombos();
    }

    public Controller switchView(String view) {
        return switchViewRaw(view).getController();
    }

    public LoadedView switchViewRaw(String view) {
        LoadedView scene = this.sceneManager.loadPage(view);
        if (!scene.getRoot().equals(this.currentScene)) {
            this.contentDash.getChildren().clear();
            this.currentScene = scene.getRoot();
            this.contentDash.getChildren().add(this.currentScene);
        }
        return scene;
    }
}
