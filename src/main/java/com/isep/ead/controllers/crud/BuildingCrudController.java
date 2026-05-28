package com.isep.ead.controllers.crud;

import com.isep.ead.controllers.widgets.popup.FormPopupController;
import com.isep.ead.dao.DAO;
import com.isep.ead.models.building.*;
import com.isep.ead.models.organization.Organization;
import com.isep.ead.widgets.popup.Popup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class BuildingCrudController extends CrudController{
        @FXML
        private Label nameOrga;
        private Organization organization;
        private final DAO<Building> dao = new DAO<>(Building.class);

        public void setOrganization(int organizationId) {
            DAO<Organization> organizationDAO = new DAO<>(Organization.class);
            this.organization = organizationDAO.getById(organizationId);
        }
        @Override
        public void index() {
            this.nameOrga.setText(this.organization.getName());
        }

        @Override
        public void add() {
            Popup popup = this.sceneManager.loadPopup("views/popup/FormPopup");
            popup.setTitle("Ajout d'un nouveau bâtiment");
            FormPopupController controller  = (FormPopupController) popup.getController();
            controller.setPopupName("Ajouter un nouveau bâtiment");
            controller.addField("name", "Nom du bâtiment *");
            controller.addField("address", "Adresse *");
            controller.addField("surface", "Surface (m²) *");
            Map<String, Supplier<Building>> map = Map.of(
                    "House", House::new,
                    "Appartment", Appartment::new,
                    "Office", Office::new,
                    "Shop", Shop::new
            );
            controller.addComboField(
                    "type", "Type de bâtiment *", new ArrayList<>(List.of("House", "Appartment", "Office", "Shop")));
            controller.setOnSubmitAction(() -> {
                System.out.println();
                Building building = map.get(controller.getValues("type")).get();
                System.out.println(building.getClass().getSimpleName());

            });
            popup.show();

        }

}


