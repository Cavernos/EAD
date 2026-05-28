package com.isep.ead.controllers.templates;

import com.isep.ead.controllers.MainController;
import com.isep.ead.controllers.crud.BuildingCrudController;
import com.isep.ead.controllers.crud.OrganizationCrudController;
import com.isep.ead.models.organization.Organization;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class OrganizationItemController extends TemplateController {

    @FXML
    public Label lblCardBuildings;
    @FXML
    public Label lblCardTitle;
    @FXML
    public Button modifyButton;
    @FXML
    public Button deleteButton;

    public void setData(Organization organization) {
        this.itemId = organization.getId();
        this.lblCardTitle.setText(organization.getName());
        this.lblCardBuildings.setText(organization.getBuildings().size() + " " + this.lblCardBuildings.getText());
        super.setData("organisation-view");

    }

    @FXML
    private void onModify() {
        super.modify();
    }

    @FXML
    private void onDelete() {
        super.delete();
    }

    public void switchToConsoView(MouseEvent mouseEvent) {
        BuildingCrudController controller =
                (BuildingCrudController) ((MainController)this.sceneManager.loadPage("menu-view").getController()).switchView("batiment-view");
        controller.setOrganization(this.itemId);
        controller.index();
    }
}
