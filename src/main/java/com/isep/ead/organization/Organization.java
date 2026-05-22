package com.isep.ead.organization;

import com.isep.ead.building.Building;
import com.isep.ead.model.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Représente une organisation regroupant plusieurs bâtiments.
 * Point d'agrégation principal des consommations énergétiques.
 */
public class Organization extends Model {

    protected int id;
    private String name;
    private List<Building> buildings;

    public Organization(int id, String name) {
        this.id = id;
        this.name = name;
        this.buildings = new ArrayList<>();
    }

    // ── Gestion des bâtiments ─────────────────────────────────

    /** Ajoute un bâtiment à l'organisation. */
    public void addBuilding(Building b) {
        if (b != null && !buildings.contains(b)) {
            buildings.add(b);
        }
    }

    /** Retire un bâtiment de l'organisation. */
    public void removeBuilding(Building b) {
        buildings.remove(b);
    }

    /** Retourne la liste des bâtiments. */
    public List<Building> getBuildings() {
        return new ArrayList<>(buildings);
    }

    /** Retourne le nombre de bâtiments. */
    public int getNumberOfBuildings() {
        return buildings.size();
    }

    // ── Calculs métier ────────────────────────────────────────

    /** Somme des consommations de tous les bâtiments. */
    public double getTotalConsumption() {
        return buildings.stream()
                .mapToDouble(Building::getTotalConsumption)
                .sum();
    }

    /** Coût estimé total de tous les bâtiments. */
    public double getEstimatedCost() {
        return buildings.stream()
                .mapToDouble(Building::getEstimatedCost)
                .sum();
    }

    /**
     * Retourne le bâtiment ayant la consommation totale la plus élevée.
     *
     * @return le bâtiment le plus consommateur, ou null si la liste est vide
     */
    public Building getMostConsumingBuilding() {
        return buildings.stream()
                .max(Comparator.comparingDouble(Building::getTotalConsumption))
                .orElse(null);
    }

    /**
     * Consommation journalière estimée : totale / 365.
     */
    public double getDailyConsumption() {
        return getTotalConsumption() / 365.0;
    }

    /**
     * Consommation mensuelle estimée : totale / 12.
     */
    public double getMonthlyConsumption() {
        return getTotalConsumption() / 12.0;
    }

    /**
     * Consommation annuelle : identique à la consommation totale enregistrée.
     */
    public double getAnnualConsumption() {
        return getTotalConsumption();
    }

    // ── CSV ───────────────────────────────────────────────────

    @Override
    public String toCSV() {
        return "ORGANIZATION;" + id + ";" + name;
    }

    public static Organization fromCSV(String csv) {
        String[] parts = csv.split(";");
        return new Organization(Integer.parseInt(parts[1]), parts[2]);
    }

    // ── Getters / Setters ─────────────────────────────────────

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "Organization{id=" + id + ", name='" + name + "', buildings=" + buildings.size()
                + ", totalCost=" + String.format("%.2f", getEstimatedCost()) + "€}";
    }
}

