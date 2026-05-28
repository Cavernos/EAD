package com.isep.ead.controllers;

import com.isep.ead.dao.DAO;
import com.isep.ead.models.building.Building;
import com.isep.ead.models.energy.*;
import com.isep.ead.models.organization.Organization;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

public class ComparaisonController extends Controller {

    @FXML private BarChart<String, Number> compareBarChart;
    @FXML private ComboBox<String> comboOrganization;
    @FXML private ComboBox<String> comboPeriod;
    @FXML private RadioButton radioModeTemporel;
    @FXML private RadioButton radioModeType;
    @FXML private RadioButton radioUnitMetric;
    @FXML private RadioButton radioUnitCost;
    @FXML private Button btnGenerate;
    @FXML private Label lblWorst;
    @FXML private Label lblDominant;
    @FXML private Label lblPeak;
    @FXML private Label lblChartTitle;

    @FXML
    public void initialize() {
        comboPeriod.setItems(FXCollections.observableArrayList(
            "Ce mois", "3 derniers mois", "6 derniers mois", "Cette année"));
        comboPeriod.setValue("6 derniers mois");
        btnGenerate.setOnAction(e -> generateChart());
        refreshCombos();
    }

    public void refreshCombos() {
        List<String> orgNames = new DAO<>(Organization.class).getAll().stream()
            .map(Organization::getName).collect(Collectors.toList());
        orgNames.add(0, "Toutes");
        String prev = comboOrganization.getValue();
        comboOrganization.setItems(FXCollections.observableArrayList(orgNames));
        comboOrganization.setValue(orgNames.contains(prev) ? prev : "Toutes");
        generateChart();
    }

    private List<Energy> getAllEnergy() {
        List<Energy> all = new ArrayList<>();
        all.addAll(new DAO<>(Electricity.class).getAll());
        all.addAll(new DAO<>(Gas.class).getAll());
        all.addAll(new DAO<>(Water.class).getAll());
        all.addAll(new DAO<>(Climatisation.class).getAll());
        return all;
    }

    private void generateChart() {
        compareBarChart.getData().clear();

        DAO<Building> buildingDao = new DAO<>(Building.class);
        DAO<Organization> orgDao = new DAO<>(Organization.class);

        List<Building> buildings = new ArrayList<>(buildingDao.getAll());

        // Filter by organisation
        String selectedOrg = comboOrganization.getValue();
        if (selectedOrg != null && !selectedOrg.equals("Toutes")) {
            orgDao.getAll().stream()
                .filter(o -> o.getName().equals(selectedOrg))
                .findFirst()
                .ifPresent(org -> buildings.removeIf(b -> b.getOrganizationId() != org.getId()));
        }

        if (buildings.isEmpty()) {
            lblWorst.setText("Aucun bâtiment");
            lblDominant.setText("—");
            lblPeak.setText("—");
            return;
        }

        List<Energy> energy = getAllEnergy();

        // Filter by period
        int months = 6;
        String period = comboPeriod.getValue();
        if (period != null) {
            months = switch (period) {
                case "Ce mois"         -> 1;
                case "3 derniers mois" -> 3;
                case "6 derniers mois" -> 6;
                default                -> 12;
            };
        }
        LocalDate from = LocalDate.now().minusMonths(months - 1).withDayOfMonth(1);
        energy.removeIf(r -> r.getDate().isBefore(from));

        // Keep only energy for selected buildings
        Set<Integer> buildingIds = buildings.stream().map(Building::getId).collect(Collectors.toSet());
        energy.removeIf(r -> !buildingIds.contains(r.getBuildingId()));

        boolean useCost = radioUnitCost != null && radioUnitCost.isSelected();
        boolean byType = radioModeType != null && radioModeType.isSelected();

        if (byType) {
            // X axis = building names, each series = energy type
            if (lblChartTitle != null) lblChartTitle.setText("Comparaison par type d'énergie");
            String[] types = {"Electricity", "Gas", "Water", "Climatisation"};
            for (String type : types) {
                XYChart.Series<String, Number> series = new XYChart.Series<>();
                series.setName(type);
                for (Building b : buildings) {
                    String bName = b.getName() != null ? b.getName() : "B" + b.getId();
                    double val = energy.stream()
                        .filter(r -> r.getBuildingId() == b.getId() && r.getClass().getSimpleName().equals(type))
                        .mapToDouble(useCost ? Energy::getEstimatedCost : Energy::getQuantity).sum();
                    series.getData().add(new XYChart.Data<>(bName, val));
                }
                if (series.getData().stream().anyMatch(d -> d.getYValue().doubleValue() > 0)) {
                    compareBarChart.getData().add(series);
                }
            }
        } else {
            // X axis = months, each series = one building
            if (lblChartTitle != null) lblChartTitle.setText("Évolution par bâtiment sur " + months + " mois");
            LocalDate now = LocalDate.now();
            for (Building b : buildings) {
                XYChart.Series<String, Number> series = new XYChart.Series<>();
                series.setName(b.getName() != null ? b.getName() : "B" + b.getId());
                for (int i = months - 1; i >= 0; i--) {
                    LocalDate m = now.minusMonths(i);
                    double val = energy.stream()
                        .filter(r -> r.getBuildingId() == b.getId()
                            && r.getDate().getYear() == m.getYear()
                            && r.getDate().getMonth() == m.getMonth())
                        .mapToDouble(useCost ? Energy::getEstimatedCost : Energy::getQuantity).sum();
                    String label = m.getMonth().getDisplayName(TextStyle.SHORT, Locale.FRENCH)
                        + (months > 6 ? " " + (m.getYear() % 100) : "");
                    series.getData().add(new XYChart.Data<>(label, val));
                }
                compareBarChart.getData().add(series);
            }
        }

        updateKpis(buildings, energy, useCost);
    }

    private void updateKpis(List<Building> buildings, List<Energy> energy, boolean useCost) {
        // Worst building
        buildings.stream()
            .max(Comparator.comparingDouble(b -> energy.stream()
                .filter(r -> r.getBuildingId() == b.getId())
                .mapToDouble(useCost ? Energy::getEstimatedCost : Energy::getQuantity).sum()))
            .ifPresentOrElse(b -> {
                double val = energy.stream().filter(r -> r.getBuildingId() == b.getId())
                    .mapToDouble(useCost ? Energy::getEstimatedCost : Energy::getQuantity).sum();
                lblWorst.setText((b.getName() != null ? b.getName() : "B" + b.getId())
                    + (useCost ? String.format("  %.2f €", val) : String.format("  %.0f kWh", val)));
            }, () -> lblWorst.setText("—"));

        // Dominant energy type
        Map<String, Double> byType = new LinkedHashMap<>();
        energy.forEach(r -> byType.merge(r.getClass().getSimpleName(),
            useCost ? r.getEstimatedCost() : r.getQuantity(), Double::sum));
        byType.entrySet().stream().max(Map.Entry.comparingByValue())
            .ifPresentOrElse(e -> lblDominant.setText(e.getKey()), () -> lblDominant.setText("—"));

        // Peak month (total across all buildings)
        energy.stream()
            .collect(Collectors.groupingBy(
                r -> r.getDate().getYear() + "-" + String.format("%02d", r.getDate().getMonthValue()),
                Collectors.summingDouble(useCost ? Energy::getEstimatedCost : Energy::getQuantity)))
            .entrySet().stream().max(Map.Entry.comparingByValue())
            .ifPresentOrElse(e -> lblPeak.setText(e.getKey() +
                (useCost ? String.format("  (%.2f €)", e.getValue()) : String.format("  (%.0f kWh)", e.getValue()))),
                () -> lblPeak.setText("—"));
    }
}
