package com.isep.ead.controllers.crud;

import com.isep.ead.controllers.widgets.inputs.FormInputController;
import com.isep.ead.controllers.widgets.popup.FormPopupController;
import com.isep.ead.dao.DAO;
import com.isep.ead.models.building.*;
import com.isep.ead.models.energy.*;
import com.isep.ead.models.organization.Organization;
import com.isep.ead.templates.BuildingItem;
import com.isep.ead.widgets.popup.Popup;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class BuildingCrudController extends CrudController {

    // batiment-view.fxml
    @FXML private Label nameOrga;
    @FXML private TilePane organizationsGrid;

    // batiment-info-view.fxml
    @FXML private Label lblEnergyTypes;
    @FXML private Label lblTotalMonthly;
    @FXML private Label lblMonthlyCost;
    @FXML private StackedBarChart<String, Number> stackedBarChart;

    private int currentBuildingId = -1;
    private Organization organization;
    private final DAO<Building> dao = new DAO<>(Building.class);

    @FXML
    protected void initialize() {
        super.initialize();
    }

    public void setOrganization(int organizationId) {
        DAO<Organization> orgDAO = new DAO<>(Organization.class);
        this.organization = orgDAO.getById(organizationId);
    }

    /** Réinitialise l'organisation courante (ex: après suppression d'une org). */
    public void clearOrganization() {
        this.organization = null;
        if (organizationsGrid != null) organizationsGrid.getChildren().clear();
        if (nameOrga != null) nameOrga.setText("");
    }

    @Override
    public void index() {
        if (organization == null) return;
        if (nameOrga != null) this.nameOrga.setText(this.organization.getName());
        if (organizationsGrid == null) return;
        List<Building> buildings = this.dao.getAll().stream()
            .filter(b -> b.getOrganizationId() == this.organization.getId())
            .toList();
        organizationsGrid.getChildren().clear();
        for (Building b : buildings) {
            organizationsGrid.getChildren().add(new BuildingItem(b, this.sceneManager).getView());
        }
    }

    @Override
    public void add() {
        Popup popup = this.sceneManager.loadPopup("views/popup/FormPopup");
        popup.setTitle("Ajout d'un nouveau bâtiment");
        FormPopupController controller = (FormPopupController) popup.getController();
        controller.setPopupName("Ajouter un bâtiment");
        controller.addField("name", "Nom du bâtiment *");
        controller.addField("address", "Adresse *");
        controller.addField("surface", "Surface (m²) *", "", FormInputController.FieldType.DOUBLE);
        controller.addComboField("type", "Type de bâtiment *",
            new ArrayList<>(List.of("House", "Appartment", "Office", "Shop")));
        popup.show();
        popup.onSubmit(() -> {
            Map<String, Supplier<Building>> map = Map.of(
                "House", House::new, "Appartment", Appartment::new,
                "Office", Office::new, "Shop", Shop::new
            );
            Building b = map.getOrDefault(controller.getValues("type"), Building::new).get();
            b.setName(controller.getValues("name"));
            b.setAddress(controller.getValues("address"));
            try { b.setSurface(Double.parseDouble(controller.getValues("surface"))); }
            catch (NumberFormatException ignored) { b.setSurface(0); }
            b.setOrganizationId(this.organization.getId());
            this.dao.create(b);
            this.index();
            refreshOrgView();
        });
    }

    @Override
    public void modify(int id) {
        Building b = this.dao.getById(id);
        if (b == null) return;
        Popup popup = this.sceneManager.loadPopup("views/popup/FormPopup");
        popup.setTitle("Modifier le bâtiment");
        FormPopupController controller = (FormPopupController) popup.getController();
        controller.setPopupName("Modifier le bâtiment");
        controller.addField("name", "Nom du bâtiment *", b.getName());
        controller.addField("address", "Adresse *", b.getAddress());
        controller.addField("surface", "Surface (m²) *", String.valueOf(b.getSurface()), FormInputController.FieldType.DOUBLE);
        popup.show();
        popup.onSubmit(() -> {
            b.setName(controller.getValues("name"));
            b.setAddress(controller.getValues("address"));
            try { b.setSurface(Double.parseDouble(controller.getValues("surface"))); }
            catch (NumberFormatException ignored) {}
            this.dao.update(b);
            this.index();
            refreshOrgView();
        });
    }

    @Override
    public void delete(int id) {
        deleteEnergyForBuilding(id);
        this.dao.remove(this.dao.getById(id));
        this.index();
        refreshOrgView();
    }

    public void clone(int id) {
        Building original = this.dao.getById(id);
        if (original == null) return;
        Building copy;
        if (original instanceof House h) {
            House ch = new House(); ch.setNumberOfRooms(h.getNumberOfRooms()); ch.setHasGarden(h.hasGarden()); copy = ch;
        } else if (original instanceof Appartment ap) {
            Appartment ca = new Appartment(); ca.setFloor(ap.getFloor()); ca.setResidenceName(ap.getResidenceName()); copy = ca;
        } else if (original instanceof Office of) {
            Office co = new Office(); co.setNumberOfRooms(of.getNumberOfRooms()); co.setNumberOfEmployees(of.getNumberOfEmployees()); copy = co;
        } else if (original instanceof Shop sh) {
            Shop cs = new Shop(); cs.setActivitySector(sh.getActivitySector()); copy = cs;
        } else {
            copy = new Building();
        }
        copy.setName(original.getName() + " (copie)");
        copy.setAddress(original.getAddress());
        copy.setSurface(original.getSurface());
        copy.setOrganizationId(original.getOrganizationId());
        this.dao.create(copy);
        this.index();
        refreshOrgView();
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

    /**
     * Rafraîchit la vue organisation (compteur bâtiments à jour).
     * N'utilise que l'instance déjà cachée — ne crée pas de vue inutilement.
     */
    private void refreshOrgView() {
        var lv = this.sceneManager.getCachedPage("organisation-view");
        if (lv != null && lv.getController() instanceof OrganizationCrudController c) {
            c.index();
        }
    }

    public void addEnergy(int buildingId) {
        Popup popup = this.sceneManager.loadPopup("views/popup/FormPopup");
        popup.setTitle("Ajouter une consommation");
        FormPopupController controller = (FormPopupController) popup.getController();
        controller.setPopupName("Nouvelle consommation");
        controller.addComboField("type", "Type d'énergie *",
            new ArrayList<>(List.of("Electricity", "Gas", "Water", "Climatisation")));
        controller.addField("date", "Date *", LocalDate.now().toString(), FormInputController.FieldType.DATE);
        controller.addField("quantity", "Quantité (kWh / m³) *", "", FormInputController.FieldType.DOUBLE);
        controller.addField("price", "Prix unitaire (EUR) *", "", FormInputController.FieldType.DOUBLE);

        controller.onComboChange("type", type -> {
            controller.removeField("extra");
            switch (type) {
                case "Electricity" -> controller.addComboField("extra", "Heures creuses",
                    new ArrayList<>(List.of("Non", "Oui")));
                case "Water" -> controller.addComboField("extra", "Eau chaude",
                    new ArrayList<>(List.of("Non", "Oui")));
                case "Climatisation" -> controller.addField("extra", "Température cible (°C)", "20.0", FormInputController.FieldType.DOUBLE);
            }
        });

        popup.show();
        popup.onSubmit(() -> {
            try {
                String type = controller.getValues("type");
                LocalDate date = LocalDate.parse(controller.getValues("date"));
                double qty = Double.parseDouble(controller.getValues("quantity"));
                double price = Double.parseDouble(controller.getValues("price"));
                String extra = controller.getValues("extra");
                switch (type) {
                    case "Electricity" -> {
                        Electricity e = new Electricity(date, qty, price, "Oui".equals(extra));
                        e.setBuildingId(buildingId);
                        new DAO<>(Electricity.class).create(e);
                    }
                    case "Gas" -> {
                        Gas g = new Gas(date, qty, price);
                        g.setBuildingId(buildingId);
                        new DAO<>(Gas.class).create(g);
                    }
                    case "Water" -> {
                        Water w = new Water(date, qty, price, "Oui".equals(extra));
                        w.setBuildingId(buildingId);
                        new DAO<>(Water.class).create(w);
                    }
                    case "Climatisation" -> {
                        double temp = 20.0;
                        try { temp = Double.parseDouble(extra); } catch (Exception ignored) {}
                        Climatisation c = new Climatisation(date, qty, price, temp);
                        c.setBuildingId(buildingId);
                        new DAO<>(Climatisation.class).create(c);
                    }
                }
                // Rafraîchir les KPIs si on est sur la vue détail bâtiment
                if (currentBuildingId == buildingId && lblEnergyTypes != null) {
                    Platform.runLater(() -> setBuilding(buildingId));
                }
            } catch (Exception e) {
                System.err.println("Erreur ajout consommation : " + e.getMessage());
            }
        });
    }

    public void setBuilding(int buildingId) {
        this.currentBuildingId = buildingId;
        Building b = this.dao.getById(buildingId);
        if (b == null) return;

        if (nameOrga != null) nameOrga.setText(b.getName() != null ? b.getName() : "Bâtiment");

        List<Energy> energy = new ArrayList<>();
        energy.addAll(new DAO<>(Electricity.class).getAll().stream().filter(e -> e.getBuildingId() == buildingId).toList());
        energy.addAll(new DAO<>(Gas.class).getAll().stream().filter(e -> e.getBuildingId() == buildingId).toList());
        energy.addAll(new DAO<>(Water.class).getAll().stream().filter(e -> e.getBuildingId() == buildingId).toList());
        energy.addAll(new DAO<>(Climatisation.class).getAll().stream().filter(e -> e.getBuildingId() == buildingId).toList());

        LocalDate now = LocalDate.now();
        List<Energy> monthly = energy.stream()
            .filter(e -> e.getDate().getYear() == now.getYear() && e.getDate().getMonth() == now.getMonth())
            .toList();

        long typeCount = energy.stream().map(e -> e.getClass().getSimpleName()).distinct().count();
        if (lblEnergyTypes != null) lblEnergyTypes.setText(typeCount + " type(s)");

        double monthlyTotal = monthly.stream().mapToDouble(Energy::getQuantity).sum();
        if (lblTotalMonthly != null) lblTotalMonthly.setText(String.format("%.0f kWh", monthlyTotal));

        double monthlyCost = monthly.stream().mapToDouble(Energy::getEstimatedCost).sum();
        if (lblMonthlyCost != null) lblMonthlyCost.setText(String.format("%.2f €", monthlyCost));

        if (stackedBarChart != null) {
            stackedBarChart.getData().clear();
            String[] types = {"Electricity", "Gas", "Water", "Climatisation"};
            for (String t : types) {
                XYChart.Series<String, Number> series = new XYChart.Series<>();
                series.setName(t);
                energy.stream().filter(e -> e.getClass().getSimpleName().equals(t))
                    .collect(java.util.stream.Collectors.groupingBy(
                        e -> e.getDate().getYear() + "-" + String.format("%02d", e.getDate().getMonthValue())))
                    .entrySet().stream().sorted(Map.Entry.comparingByKey())
                    .forEach(en -> series.getData().add(new XYChart.Data<>(en.getKey(),
                        en.getValue().stream().mapToDouble(Energy::getQuantity).sum())));
                if (!series.getData().isEmpty()) stackedBarChart.getData().add(series);
            }
        }
    }

    @FXML
    private void onAddEnergyFromInfo() {
        if (currentBuildingId >= 0) {
            addEnergy(currentBuildingId);
        }
    }

    @FXML
    private void onImportCsvFromInfo() {
        if (currentBuildingId < 0) return;
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Importer des relevés d'énergie");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers CSV", "*.csv"));
        File file = chooser.showOpenDialog(null);
        if (file == null) return;

        int imported = 0;
        int errors = 0;
        StringBuilder errorLog = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNum = 0;
            while ((line = br.readLine()) != null) {
                lineNum++;
                line = line.trim();
                if (line.isBlank() || line.startsWith("#")) continue;
                // Skip header row (starts with "date" or "type")
                if (lineNum == 1 && (line.toLowerCase().startsWith("date") || line.toLowerCase().startsWith("type"))) continue;
                String[] f = line.split(",", -1);
                if (f.length < 4) { errors++; errorLog.append("Ligne ").append(lineNum).append(": colonnes insuffisantes\n"); continue; }
                try {
                    // Expected format: date,type,quantity,pricePerUnit[,extra]
                    LocalDate date = LocalDate.parse(f[0].trim());
                    String type = f[1].trim();
                    double qty = Double.parseDouble(f[2].trim());
                    double price = Double.parseDouble(f[3].trim());
                    String extra = f.length > 4 ? f[4].trim() : "";
                    switch (type) {
                        case "Electricity" -> {
                            Electricity e = new Electricity(date, qty, price, "true".equalsIgnoreCase(extra) || "Oui".equalsIgnoreCase(extra) || "1".equals(extra));
                            e.setBuildingId(currentBuildingId);
                            new DAO<>(Electricity.class).create(e);
                        }
                        case "Gas" -> {
                            Gas g = new Gas(date, qty, price);
                            g.setBuildingId(currentBuildingId);
                            new DAO<>(Gas.class).create(g);
                        }
                        case "Water" -> {
                            Water w = new Water(date, qty, price, "true".equalsIgnoreCase(extra) || "Oui".equalsIgnoreCase(extra) || "1".equals(extra));
                            w.setBuildingId(currentBuildingId);
                            new DAO<>(Water.class).create(w);
                        }
                        case "Climatisation" -> {
                            double temp = 20.0;
                            try { temp = Double.parseDouble(extra); } catch (Exception ignored) {}
                            Climatisation c = new Climatisation(date, qty, price, temp);
                            c.setBuildingId(currentBuildingId);
                            new DAO<>(Climatisation.class).create(c);
                        }
                        default -> { errors++; errorLog.append("Ligne ").append(lineNum).append(": type inconnu '").append(type).append("'\n"); continue; }
                    }
                    imported++;
                } catch (Exception ex) {
                    errors++;
                    errorLog.append("Ligne ").append(lineNum).append(": ").append(ex.getMessage()).append("\n");
                }
            }
        } catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Erreur de lecture");
            a.setContentText("Impossible de lire le fichier : " + ex.getMessage());
            a.show();
            return;
        }

        Alert result = new Alert(errors == 0 ? Alert.AlertType.INFORMATION : Alert.AlertType.WARNING);
        result.setTitle("Import terminé");
        result.setHeaderText(imported + " relevé(s) importé(s)" + (errors > 0 ? ", " + errors + " erreur(s)" : ""));
        if (errors > 0) result.setContentText(errorLog.toString());
        result.show();

        if (imported > 0) Platform.runLater(() -> setBuilding(currentBuildingId));
    }

    @FXML
    private void onGenerateTestData() {
        generateTestData();
    }

    public void generateTestData() {
        if (organization == null) return;
        List<Building> buildings = this.dao.getAll().stream()
            .filter(b -> b.getOrganizationId() == this.organization.getId()).toList();

        if (buildings.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun bâtiment");
            alert.setHeaderText(null);
            alert.setContentText("Aucun bâtiment trouvé pour cette organisation.\nCreéz d'abord un bâtiment via le bouton \"+\".");
            alert.show();
            return;
        }

        int currentYear = LocalDate.now().getYear();
        for (Building b : buildings) {
            // Supprimer les données existantes pour ce bâtiment avant de régénérer
            deleteEnergyForBuilding(b.getId());
            for (int month = 1; month <= 12; month++) {
                LocalDate d = LocalDate.of(currentYear, month, 1);
                Electricity e = new Electricity(d, 100 + Math.random() * 400, 0.18, month % 2 == 0);
                e.setBuildingId(b.getId());
                new DAO<>(Electricity.class).create(e);
                Gas g = new Gas(d, 50 + Math.random() * 200, 0.09);
                g.setBuildingId(b.getId());
                new DAO<>(Gas.class).create(g);
                Water w = new Water(d, 20 + Math.random() * 80, 0.004, false);
                w.setBuildingId(b.getId());
                new DAO<>(Water.class).create(w);
                Climatisation c = new Climatisation(d, 30 + Math.random() * 100, 0.20, 22.0);
                c.setBuildingId(b.getId());
                new DAO<>(Climatisation.class).create(c);
            }
        }
        index();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Données générées");
        alert.setHeaderText(null);
        alert.setContentText("✅ Données de test générées pour " + buildings.size() + " bâtiment(s).\n"
            + "Consultez le tableau de bord ou cliquez sur un bâtiment pour voir les graphiques.");
        alert.show();
    }
}
