package com.isep.ead.controllers.templates;

import com.isep.ead.controllers.crud.OrganizationCrudController;
import com.isep.ead.models.organization.Organization;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class OrganizationItemController extends TemplateController {

    @FXML
    public Label lblCardBuildings;
    @FXML
    public Label lblCardTitle;
    @FXML
    public Button modifyButton;
    @FXML
    public Button deleteButton;

    private OrganizationCrudController crudController;

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
}
