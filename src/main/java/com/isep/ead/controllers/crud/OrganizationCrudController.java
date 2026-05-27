package com.isep.ead.controllers.crud;

import com.isep.ead.controllers.Controller;
import com.isep.ead.controllers.widgets.popup.FormPopupController;
import com.isep.ead.dao.DAO;
import com.isep.ead.models.organization.Organization;
import com.isep.ead.templates.OrganizationItem;
import com.isep.ead.utils.LoadedView;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class OrganizationCrudController extends CrudController {
    @FXML
    public TilePane organizationsGrid;

    @FXML
    protected void initialize() {
        super.initialize();
    }

    private final DAO<Organization> dao = new DAO<>(Organization.class);

    @Override
    public void index() {
        Organization[] organizations = this.dao.getAll().toArray(new Organization[0]);
        ObservableList<Node> organizationsElements =  this.organizationsGrid.getChildren();
        organizationsElements.clear();
        for (Organization organization : organizations) {
            organizationsElements.add(new OrganizationItem(organization, this.sceneManager).getView());

        }

    }

    @Override
    public void add() {
        Stage stage = new Stage();
        this.sceneManager.setMainStage(stage);
        stage.setTitle("Ajout d'une organisation");
        LoadedView view = this.sceneManager.loadTemplate("views/popup/FormPopup");
        this.sceneManager.setMainStage(stage);
        FormPopupController controller = (FormPopupController) this.sceneManager.switchTo(view);
        controller.setPopupName("Nouvelle Organisation");
        controller.addField("name", "Nom de l'organisation *");
        controller.addField("owner", "Propriétaire");
        stage.show();
        controller.setOnSubmitAction(() -> {
            Organization organization = new Organization();
            organization.setName(controller.getValues("name"));
            organization.setOwner(controller.getValues("owner"));
            this.dao.create(organization);
            stage.close();
            this.index();
        });

    }

    public void modify(int id) {
        Stage stage = new Stage();
        Organization organization = this.dao.getById(id);
        stage.setTitle("Modification d'une organisation");
        LoadedView view = this.sceneManager.loadTemplate("views/popup/FormPopup");
        this.sceneManager.setMainStage(stage);
        FormPopupController controller = (FormPopupController) this.sceneManager.switchTo(view);
        controller.setPopupName("Modifier l'Organisation");
        controller.addField("name", "Nom de l'organisation *", organization.getName());
        controller.addField("owner", "Propriétaire");
        stage.show();
        controller.setOnSubmitAction(() -> {
            organization.setName(controller.getValues("name"));
            organization.setOwner(controller.getValues("owner"));
            this.dao.update(organization);
            stage.close();
            this.index();
        });


    }

    public void delete(int id) {
        //System.out.println(id);
        this.dao.remove(this.dao.getById(id));
        this.index();
    }


}
