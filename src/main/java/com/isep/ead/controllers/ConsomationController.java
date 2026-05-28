package com.isep.ead.controllers;

import com.isep.ead.dao.DAO;
import com.isep.ead.models.building.Building;
import com.isep.ead.models.energy.*;
import com.isep.ead.models.organization.Organization;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConsomationController extends Controller {

    // -- Graphique --
    @FXML private BarChart<String, Number> barChart;
    @FXML private ComboBox<String> comboOrganization;
    @FXML private ComboBox<String> comboBuilding;
    @FXML private ComboBox<String> comboEnergyType;
    @FXML private ComboBox<String> comboPeriod;
    @FXML private RadioButton radioUnitMetric;
    @FXML private RadioButton radioUnitCost;
    @FXML private Button btnApplyFilters;

    // -- Historique --
    @FXML private TableView<Energy> tableView;
    @FXML private TableColumn<Energy, String> colDate;
    @FXML private TableColumn<Energy, String> colBuilding1;
    @FXML private TableColumn<Energy, String> colBuilding;
    @FXML private TableColumn<Energy, String> colEnergy;
    @FXML private TableColumn<Energy, String> colAmount;
    @FXML private TableColumn<Energy, String> colCost;

    private final DAO<Building>      buildingDao = new DAO<>(Building.class);
    private final DAO<Organization>  orgDao      = new DAO<>(Organization.class);
    private final DAO<Electricity>   elecDao     = new DAO<>(Electricity.class);
    private final DAO<Gas>           gasDao      = new DAO<>(Gas.class);
    private final DAO<Water>         waterDao    = new DAO<>(Water.class);
    private final DAO<Climatisation> climaDao    = new DAO<>(Climatisation.class);

    @FXML
    public void initialize() {
        try {
            if (comboEnergyType != null)   comboEnergyType.setItems(FXCollections.observableArrayList("Tous","Electricity","Gas","Water","Climatisation"));
            if (comboPeriod != null)       comboPeriod.setItems(FXCollections.observableArrayList("Ce mois","3 derniers mois","6 derniers mois","Cette année"));
            if (btnApplyFilters != null)   btnApplyFilters.setOnAction(e -> applyFilters());
            if (colDate != null)           initTableColumns();
            refreshCombos();
        } catch (Exception e) {
            System.err.println("ConsomationController init: " + e.getMessage());
        }
    }

    public void refreshCombos() {
        List<String> orgNames = orgDao.getAll().stream().map(Organization::getName).collect(Collectors.toList());
        orgNames.add(0, "Toutes");
        if (comboOrganization != null) {
            String prev = comboOrganization.getValue();
            comboOrganization.valueProperty().removeListener((obs, o, n) -> {});
            comboOrganization.setItems(FXCollections.observableArrayList(orgNames));
            comboOrganization.setValue(orgNames.contains(prev) ? prev : "Toutes");
            comboOrganization.valueProperty().addListener((obs, oldVal, newVal) -> refreshBuildingCombo(newVal));
        }
        refreshBuildingCombo(comboOrganization != null ? comboOrganization.getValue() : "Toutes");
        List<Energy> all = getAllEnergy();
        if (colDate != null) loadHistorique(all);
        if (barChart != null) loadBarChart(all);
    }

    private void refreshBuildingCombo(String orgName) {
        if (comboBuilding == null) return;
        List<String> buildingNames = new ArrayList<>();
        buildingNames.add("Tous");
        if (orgName != null && !orgName.equals("Toutes")) {
            orgDao.getAll().stream()
                .filter(o -> o.getName().equals(orgName))
                .findFirst()
                .ifPresent(org -> buildingDao.getAll().stream()
                    .filter(b -> b.getOrganizationId() == org.getId())
                    .map(Building::getName)
                    .forEach(buildingNames::add));
        } else {
            buildingDao.getAll().stream().map(Building::getName).forEach(buildingNames::add);
        }
        comboBuilding.setItems(FXCollections.observableArrayList(buildingNames));
        comboBuilding.setValue("Tous");
    }

    private List<Energy> getAllEnergy() {
        List<Energy> all = new ArrayList<>();
        all.addAll(elecDao.getAll());
        all.addAll(gasDao.getAll());
        all.addAll(waterDao.getAll());
        all.addAll(climaDao.getAll());
        return all;
    }

    private void initTableColumns() {
        colDate.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDate().toString()));
        colBuilding1.setCellValueFactory(c -> {
            Building b = buildingDao.getById(c.getValue().getBuildingId());
            if (b == null) return new SimpleStringProperty("—");
            return orgDao.getAll().stream()
                .filter(o -> o.getId() == b.getOrganizationId()).findFirst()
                .map(o -> new SimpleStringProperty(o.getName()))
                .orElse(new SimpleStringProperty("—"));
        });
        colBuilding.setCellValueFactory(c -> {
            Building b = buildingDao.getById(c.getValue().getBuildingId());
            return new SimpleStringProperty(b != null ? b.getName() : "—");
        });
        colEnergy.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getClass().getSimpleName()));
        colAmount.setCellValueFactory(c -> new SimpleStringProperty(String.format("%.2f", c.getValue().getQuantity())));
        colCost.setCellValueFactory(c -> new SimpleStringProperty(String.format("%.2f €", c.getValue().getEstimatedCost())));
    }

    private void applyFilters() {
        List<Energy> records = getAllEnergy();

        // Filter by organisation
        if (comboOrganization != null && comboOrganization.getValue() != null
                && !comboOrganization.getValue().equals("Toutes")) {
            String orgName = comboOrganization.getValue();
            orgDao.getAll().stream().filter(o -> o.getName().equals(orgName)).findFirst().ifPresent(org -> {
                Set<Integer> buildingIds = buildingDao.getAll().stream()
                    .filter(b -> b.getOrganizationId() == org.getId())
                    .map(Building::getId).collect(Collectors.toSet());
                records.removeIf(r -> !buildingIds.contains(r.getBuildingId()));
            });
        }

        // Filter by building
        if (comboBuilding != null && comboBuilding.getValue() != null
                && !comboBuilding.getValue().equals("Tous")) {
            String bName = comboBuilding.getValue();
            buildingDao.getAll().stream().filter(b -> b.getName().equals(bName)).findFirst().ifPresent(b -> {
                int bid = b.getId();
                records.removeIf(r -> r.getBuildingId() != bid);
            });
        }

        if (comboPeriod != null && comboPeriod.getValue() != null) {
            LocalDate from = switch (comboPeriod.getValue()) {
                case "Ce mois"         -> LocalDate.now().withDayOfMonth(1);
                case "3 derniers mois" -> LocalDate.now().minusMonths(3);
                case "6 derniers mois" -> LocalDate.now().minusMonths(6);
                default                -> LocalDate.now().withDayOfYear(1);
            };
            records.removeIf(r -> r.getDate().isBefore(from));
        }

        if (comboEnergyType != null && comboEnergyType.getValue() != null && !comboEnergyType.getValue().equals("Tous")) {
            String t = comboEnergyType.getValue();
            records.removeIf(r -> !r.getClass().getSimpleName().equals(t));
        }

        if (barChart != null)  loadBarChart(records);
        if (tableView != null) loadHistorique(records);
    }

    private void loadBarChart(List<Energy> records) {
        barChart.getData().clear();
        boolean useCost = radioUnitCost != null && radioUnitCost.isSelected();
        Map<String, List<Energy>> grouped = records.stream().collect(
            Collectors.groupingBy(r -> r.getDate().getYear() + "-" + String.format("%02d", r.getDate().getMonthValue())));
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(useCost ? "Coût (€)" : "Consommation");
        grouped.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(e -> {
            double val = useCost
                ? e.getValue().stream().mapToDouble(Energy::getEstimatedCost).sum()
                : e.getValue().stream().mapToDouble(Energy::getQuantity).sum();
            series.getData().add(new XYChart.Data<>(e.getKey(), val));
        });
        barChart.getData().add(series);
    }

    private void loadHistorique(List<Energy> records) {
        if (tableView != null) tableView.setItems(FXCollections.observableArrayList(records));
    }
}

