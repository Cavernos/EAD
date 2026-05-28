package com.isep.ead.controllers.templates;

import com.isep.ead.controllers.MainController;
import com.isep.ead.controllers.crud.BuildingCrudController;
import com.isep.ead.models.building.Building;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class BuildingItemController extends TemplateController {

    @FXML
    public Label lblCardTitle;
    @FXML
    public Label lblCardBuildings;
    @FXML
    public Button modifyButton;
    @FXML
    public Button deleteButton;
    @FXML
    public Button cloneButton;

    public void setData(Building building) {
        this.itemId = building.getId();
        this.lblCardTitle.setText(building.getName() != null ? building.getName() : "Bâtiment");
        this.lblCardBuildings.setText(building.getClass().getSimpleName());
        super.setData("batiment-view");
    }

    @FXML
    private void onModify() {
        super.modify();
    }

    @FXML
    private void onDelete() {
        super.delete();
    }

    @FXML
    private void onClone() {
        var lv = this.sceneManager.getCachedPage("batiment-view");
        if (lv != null && lv.getController() instanceof BuildingCrudController c) {
            c.clone(this.itemId);
        }
    }

    @FXML
    public void switchToConsoView(MouseEvent mouseEvent) {
        BuildingCrudController controller =
            (BuildingCrudController) ((MainController) this.sceneManager.loadPage("menu-view").getController())
                .switchView("batiment-info-view");
        controller.setBuilding(this.itemId);
    }
}
