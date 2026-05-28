package com.isep.ead.controllers;

import com.isep.ead.dao.DAO;
import com.isep.ead.models.building.Building;
import com.isep.ead.models.energy.*;
import com.isep.ead.models.organization.Organization;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

public class DashboardController extends Controller {

    @FXML private Label lblConsToday;
    @FXML private Label lblConsMonth;
    @FXML private Label lblConsYear;
    @FXML private Label lblEstimatedCost;
    @FXML private Label lblWorstBuilding;
    @FXML private Label lblWorstBuildingCons;
    @FXML private AreaChart<String, Number> trendAreaChart;
    @FXML private ListView<String> listViewAlerts;
    @FXML private ProgressBar progressCarbon;
    @FXML private ProgressBar progressEfficiency;
    @FXML private Label lblCarbonPct;
    @FXML private Label lblEfficiencyPct;

    @FXML
    public void initialize() {
        try { refresh(); }
        catch (Exception e) { System.err.println("Dashboard init error: " + e.getMessage()); }
    }

    public void refresh() {
        DAO<Building>       buildingDao    = new DAO<>(Building.class);
        DAO<Organization>   orgDao         = new DAO<>(Organization.class);
        DAO<Electricity>    electricityDao = new DAO<>(Electricity.class);
        DAO<Gas>            gasDao         = new DAO<>(Gas.class);
        DAO<Water>          waterDao       = new DAO<>(Water.class);
        DAO<Climatisation>  climaDao       = new DAO<>(Climatisation.class);

        // IDs des organisations existantes (pour filtrer les bâtiments orphelins)
        Set<Integer> validOrgIds = orgDao.getAll().stream()
            .map(Organization::getId)
            .collect(Collectors.toSet());

        // Seuls les bâtiments appartenant à une organisation existante
        List<Building> buildings = buildingDao.getAll().stream()
            .filter(b -> validOrgIds.contains(b.getOrganizationId()))
            .toList();

        List<Energy> allEnergy = new ArrayList<>();
        allEnergy.addAll(electricityDao.getAll());
        allEnergy.addAll(gasDao.getAll());
        allEnergy.addAll(waterDao.getAll());
        allEnergy.addAll(climaDao.getAll());

        // Filtrer l'énergie pour ne garder que celle liée à des bâtiments valides
        Set<Integer> validBuildingIds = buildings.stream()
            .map(Building::getId)
            .collect(Collectors.toSet());
        List<Energy> validEnergy = allEnergy.stream()
            .filter(e -> validBuildingIds.isEmpty() || validBuildingIds.contains(e.getBuildingId()))
            .toList();
        // Si aucun bâtiment valide, toute l'énergie est orpheline → ignorer
        List<Energy> energy = buildings.isEmpty() ? List.of() : validEnergy;

        LocalDate now = LocalDate.now();

        // Consommation du jour
        double today = energy.stream()
            .filter(r -> r.getDate().equals(now))
            .mapToDouble(Energy::getQuantity).sum();
        lblConsToday.setText(String.format("%.0f kWh", today));

        // Consommation du mois
        double month = energy.stream()
            .filter(r -> r.getDate().getYear() == now.getYear() && r.getDate().getMonth() == now.getMonth())
            .mapToDouble(Energy::getQuantity).sum();
        lblConsMonth.setText(String.format("%.0f kWh", month));

        // Consommation annuelle
        double year = energy.stream()
            .filter(r -> r.getDate().getYear() == now.getYear())
            .mapToDouble(Energy::getQuantity).sum();
        lblConsYear.setText(String.format("%.0f kWh", year));

        // Coût mensuel estimé
        double cost = energy.stream()
            .filter(r -> r.getDate().getYear() == now.getYear() && r.getDate().getMonth() == now.getMonth())
            .mapToDouble(Energy::getEstimatedCost).sum();
        lblEstimatedCost.setText(String.format("%.2f €", cost));

        // Bâtiment le plus consommateur (uniquement si consommation > 0)
        buildings.stream()
            .filter(b -> energy.stream().anyMatch(r -> r.getBuildingId() == b.getId()))
            .max(Comparator.comparingDouble(b ->
                energy.stream().filter(r -> r.getBuildingId() == b.getId())
                    .mapToDouble(Energy::getQuantity).sum()))
            .ifPresentOrElse(b -> {
                double cons = energy.stream().filter(r -> r.getBuildingId() == b.getId())
                    .mapToDouble(Energy::getQuantity).sum();
                lblWorstBuilding.setText(b.getName() != null ? b.getName() : "—");
                lblWorstBuildingCons.setText(String.format("%.0f kWh consommés", cons));
            }, () -> {
                lblWorstBuilding.setText("—");
                lblWorstBuildingCons.setText("Aucune donnée");
            });

        // Courbe AreaChart 12 derniers mois
        trendAreaChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Consommation");
        for (int i = 11; i >= 0; i--) {
            LocalDate m = now.minusMonths(i);
            double val = energy.stream()
                .filter(r -> r.getDate().getYear() == m.getYear() && r.getDate().getMonth() == m.getMonth())
                .mapToDouble(Energy::getQuantity).sum();
            String label = m.getMonth().getDisplayName(TextStyle.SHORT, Locale.FRENCH);
            series.getData().add(new XYChart.Data<>(label, val));
        }
        trendAreaChart.getData().add(series);

        // Alertes
        List<String> msgs = new ArrayList<>();
        buildings.forEach(b -> {
            double c = energy.stream().filter(r -> r.getBuildingId() == b.getId())
                .mapToDouble(Energy::getEstimatedCost).sum();
            if (c >= 5000) msgs.add("CRITIQUE – " + b.getName() + " : " + String.format("%.2f €", c));
            else if (c >= 1000) msgs.add("ATTENTION – " + b.getName() + " : " + String.format("%.2f €", c));
        });
        listViewAlerts.setItems(FXCollections.observableArrayList(
            msgs.isEmpty() ? List.of("✅ Aucune alerte active") : msgs));

        // Indicateurs de performance
        // 1. Réduction Empreinte Carbone : comparaison conso mois courant vs même mois an dernier
        double consThisMonth = energy.stream()
            .filter(r -> r.getDate().getYear() == now.getYear() && r.getDate().getMonth() == now.getMonth())
            .mapToDouble(Energy::getQuantity).sum();
        double consLastYearSameMonth = energy.stream()
            .filter(r -> r.getDate().getYear() == now.getYear() - 1 && r.getDate().getMonth() == now.getMonth())
            .mapToDouble(Energy::getQuantity).sum();
        double carbonRatio;
        if (consLastYearSameMonth > 0) {
            // réduction positive = on consomme moins qu'avant
            carbonRatio = Math.max(0, Math.min(1, 1.0 - (consThisMonth / consLastYearSameMonth)));
        } else if (consThisMonth == 0) {
            carbonRatio = 0;
        } else {
            // Pas de référence N-1 : on base sur une cible arbitraire (500 kWh/mois)
            carbonRatio = Math.max(0, Math.min(1, 1.0 - (consThisMonth / 500.0)));
        }
        if (progressCarbon != null) progressCarbon.setProgress(carbonRatio);
        if (lblCarbonPct != null) {
            if (consLastYearSameMonth > 0) {
                lblCarbonPct.setText(String.format("%.0f%%", carbonRatio * 100));
            } else {
                lblCarbonPct.setText("Pas de référence N-1");
            }
        }

        // 2. Efficacité Énergétique : % de bâtiments sous la consommation moyenne
        double efficiencyRatio;
        if (!buildings.isEmpty()) {
            double avgCons = buildings.stream()
                .mapToDouble(b -> energy.stream().filter(r -> r.getBuildingId() == b.getId())
                    .mapToDouble(Energy::getQuantity).sum())
                .average().orElse(0);
            long efficient = buildings.stream()
                .filter(b -> {
                    double c = energy.stream().filter(r -> r.getBuildingId() == b.getId())
                        .mapToDouble(Energy::getQuantity).sum();
                    return c > 0 && c <= avgCons;
                }).count();
            efficiencyRatio = avgCons > 0 ? (double) efficient / buildings.size() : 0;
        } else {
            efficiencyRatio = 0;
        }
        if (progressEfficiency != null) progressEfficiency.setProgress(efficiencyRatio);
        if (lblEfficiencyPct != null) lblEfficiencyPct.setText(String.format("%.0f%%", efficiencyRatio * 100));
    }
}
