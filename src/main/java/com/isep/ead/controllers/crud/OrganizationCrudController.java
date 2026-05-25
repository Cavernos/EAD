package com.isep.ead.controllers.crud;

import com.isep.ead.dao.DAO;
import com.isep.ead.models.organization.Organization;
import com.isep.ead.controllers.widgets.popup.FormPopupController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OrganizationCrudController extends CrudController {

    @FXML
    protected void initialize() {
        super.initialize();
        this.index();
    }

    private final DAO<Organization> dao =new DAO<>(Organization.class);

    @Override
    public void index() {
        Organization[] organizations = this.dao.getAll().toArray(new Organization[0]);
        for (Organization organization : organizations) {
            VBox vBox  = new VBox();
            Label idLabel = new Label("Id " + organization.getId());
            Label nameLabel = new Label("Name " + organization.getName());
            vBox.getChildren().addAll(idLabel, nameLabel);
            //this.organizationLayer.getChildren().add(vBox);
        }

    }

    @Override
    public void add() {
        Stage stage = new Stage();
        this.sceneManager.setMainStage(stage);
        stage.setTitle("Ajout d'une organisation");
        FormPopupController controller = (FormPopupController) this.sceneManager.switchTo("views/popup/FormPopup");
        controller.setPopupName("Nouvelle Organisation");
        controller.addField("name", "Nom de l'organisation *");
        controller.addField("owner", "Propriétaire");
        stage.show();
        controller.setOnSubmitAction(() -> {
            Organization organization = new Organization();
            organization.setName(controller.getValues("name"));
            organization.setOwner(controller.getValues("owner"));
            DAO<Organization> dao = new DAO<>(Organization.class);
            dao.create(organization);
            stage.close();
        });

    }

    @Override
    public void modify() {

    }

    @Override
    public void delete() {
        this.sceneManager.switchTo("test");
    }


}
