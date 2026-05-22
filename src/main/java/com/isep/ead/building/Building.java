package com.isep.ead.building;

import com.isep.ead.alert.Alert;
import com.isep.ead.alert.AlertLevel;
import com.isep.ead.energy.Energy;
import com.isep.ead.model.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite représentant un bâtiment.
 * Chaque bâtiment possède une liste de consommations énergétiques et d'alertes.
 */
public abstract class Building extends Model implements Cloneable {

    protected int id;
    protected String name;
    protected String address;
    protected double surface;

    protected List<Energy> energyTypes;
    protected List<Alert> alerts;

    /** Seuil de coût (€) au-delà duquel une alerte WARNING est générée. */
    private static final double COST_WARNING_THRESHOLD = 1000.0;
    /** Seuil de coût (€) au-delà duquel une alerte CRITICAL est générée. */
    private static final double COST_CRITICAL_THRESHOLD = 5000.0;

    protected Building(int id, String name, String address, double surface) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.surface = surface;
        this.energyTypes = new ArrayList<>();
        this.alerts = new ArrayList<>();
    }

    // ── Gestion des énergies ──────────────────────────────────

    /** Ajoute une consommation énergétique au bâtiment. */
    public void addEnergy(Energy e) {
        if (e != null) {
            energyTypes.add(e);
            checkAndGenerateAlerts();
        }
    }

    /** Supprime une consommation énergétique du bâtiment. */
    public void removeEnergy(Energy e) {
        energyTypes.remove(e);
    }

    /** Retourne la liste de toutes les consommations enregistrées. */
    public List<Energy> getEnergyTypes() {
        return new ArrayList<>(energyTypes);
    }

    // ── Calculs métier ────────────────────────────────────────

    /**
     * Retourne la somme des quantités consommées (toutes énergies confondues).
     */
    public double getTotalConsumption() {
        return energyTypes.stream()
                .mapToDouble(Energy::getQuantity)
                .sum();
    }

    /**
     * Retourne le coût estimé total de toutes les consommations.
     */
    public double getEstimatedCost() {
        return energyTypes.stream()
                .mapToDouble(Energy::getEstimatedCost)
                .sum();
    }

    // ── Gestion des alertes ───────────────────────────────────

    /** Retourne toutes les alertes du bâtiment. */
    public List<Alert> getAlerts() {
        return new ArrayList<>(alerts);
    }

    /** Ajoute manuellement une alerte au bâtiment. */
    public void addAlert(Alert alert) {
        if (alert != null) {
            alerts.add(alert);
        }
    }

    /** Analyse le coût estimé et génère automatiquement une alerte si nécessaire. */
    private void checkAndGenerateAlerts() {
        double cost = getEstimatedCost();
        if (cost >= COST_CRITICAL_THRESHOLD) {
            alerts.add(new Alert(
                    "Coût critique atteint : " + String.format("%.2f", cost) + " € pour le bâtiment " + name,
                    AlertLevel.CRITICAL
            ));
        } else if (cost >= COST_WARNING_THRESHOLD) {
            alerts.add(new Alert(
                    "Coût élevé : " + String.format("%.2f", cost) + " € pour le bâtiment " + name,
                    AlertLevel.WARNING
            ));
        }
    }

    // ── Clone ─────────────────────────────────────────────────

    @Override
    public Building clone() {
        try {
            Building cloned = (Building) super.clone();
            cloned.energyTypes = new ArrayList<>(this.energyTypes);
            cloned.alerts = new ArrayList<>(this.alerts);
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Clonage impossible pour " + getClass().getSimpleName(), e);
        }
    }

    // ── Getters / Setters ─────────────────────────────────────

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public double getSurface() { return surface; }
    public void setSurface(double surface) { this.surface = surface; }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{id=" + id + ", name='" + name + "', address='" + address
                + "', surface=" + surface + ", totalCost=" + String.format("%.2f", getEstimatedCost()) + "€}";
    }
}

