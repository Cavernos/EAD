package com.isep.ead.controllers.crud;

import com.isep.ead.controllers.widgets.popup.FormPopupController;
import com.isep.ead.dao.DAO;
import com.isep.ead.models.building.Building;
import com.isep.ead.models.energy.*;
import com.isep.ead.models.organization.Organization;
import com.isep.ead.templates.OrganizationItem;
import com.isep.ead.widgets.popup.Popup;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.TilePane;

import java.util.List;

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
        DAO<Building> buildingDao = new DAO<>(Building.class);
        List<Building> allBuildings = buildingDao.getAll();
        List<Organization> organizations = this.dao.getAll();
        ObservableList<Node> organizationsElements = this.organizationsGrid.getChildren();
        organizationsElements.clear();
        for (Organization org : organizations) {
            allBuildings.stream()
                .filter(b -> b.getOrganizationId() == org.getId())
                .forEach(org::addBuilding);
            organizationsElements.add(new OrganizationItem(org, this.sceneManager).getView());
        }

    }

    @Override
    public void add() {
        Popup popup = this.sceneManager.loadPopup("views/popup/FormPopup");
        popup.setTitle("Ajout d'une organisation");
        FormPopupController controller = (FormPopupController) popup.getController();
        controller.setPopupName("Nouvelle Organisation");
        controller.addField("name", "Nom de l'organisation *");
        controller.addField("owner", "Propriétaire");
        popup.show();
        popup.onSubmit(() -> {
            Organization organization = new Organization();
            organization.setName(controller.getValues("name"));
            organization.setOwner(controller.getValues("owner"));
            this.dao.create(organization);
            this.index();
        });

    }

    public void modify(int id) {
        Organization organization = this.dao.getById(id);
        Popup popup = this.sceneManager.loadPopup("views/popup/FormPopup");
        popup.setTitle("Modification d'une organisation");
        FormPopupController controller = (FormPopupController) popup.getController();
        controller.setPopupName("Modifier l'Organisation");
        controller.addField("name", "Nom de l'organisation *", organization.getName());
        controller.addField("owner", "Propriétaire");
        popup.show();
        popup.onSubmit(() -> {
            organization.setName(controller.getValues("name"));
            organization.setOwner(controller.getValues("owner"));
            this.dao.update(organization);
            this.index();
        });


    }

    public void delete(int id) {
        DAO<Building> buildingDao = new DAO<>(Building.class);
        buildingDao.getAll().stream()
            .filter(b -> b.getOrganizationId() == id)
            .forEach(b -> {
                deleteEnergyForBuilding(b.getId());
                buildingDao.remove(b);
            });
        this.dao.remove(this.dao.getById(id));
        this.index();
        // Si batiment-view est en cache, on le vide aussi
        var lv = this.sceneManager.getCachedPage("batiment-view");
        if (lv != null && lv.getController() instanceof BuildingCrudController c) {
            c.clearOrganization();
        }
    }

    private void deleteEnergyForBuilding(int buildingId) {
        DAO<Electricity>   elecDao  = new DAO<>(Electricity.class);
        DAO<Gas>           gasDao   = new DAO<>(Gas.class);
        DAO<Water>         waterDao = new DAO<>(Water.class);
        DAO<Climatisation> climaDao = new DAO<>(Climatisation.class);
        elecDao.getAll().stream().filter(e -> e.getBuildingId() == buildingId).forEach(elecDao::remove);
        gasDao.getAll().stream().filter(e -> e.getBuildingId() == buildingId).forEach(gasDao::remove);
        waterDao.getAll().stream().filter(e -> e.getBuildingId() == buildingId).forEach(waterDao::remove);
        climaDao.getAll().stream().filter(e -> e.getBuildingId() == buildingId).forEach(climaDao::remove);
    }


}
