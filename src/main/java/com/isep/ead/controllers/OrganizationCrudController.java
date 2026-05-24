package com.isep.ead.controllers;

import com.isep.ead.dao.DAO;
import com.isep.ead.models.organization.Organization;
import com.isep.ead.widgets.FormScene;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Map;

public class OrganizationCrudController extends CrudController {

    @FXML
    public HBox organizationLayer;

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
            this.organizationLayer.getChildren().add(vBox);
        }

    }

    @Override
    public void add() {
        Stage stage = new Stage();
        Organization organization = new Organization();
        FormScene form = new FormScene(Map.of("name", "Nom :"));
        stage.setScene(form.create());
        stage.show();
        form.getSubmitButton().setOnAction(actionEvent -> {
            Map<String, String> values = form.getValues();
            organization.setName(values.get("name"));
            this.dao.create(organization);
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
