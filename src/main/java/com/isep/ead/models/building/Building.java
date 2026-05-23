package com.isep.ead.models.building;

import com.isep.ead.models.Model;
import com.isep.ead.models.alert.Alert;
import com.isep.ead.models.alert.AlertLevel;
import com.isep.ead.models.energy.Energy;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite représentant un bâtiment.
 * L'id est géré par le DAO (auto-incrémenté) et ne figure pas dans le constructeur.
 */
public class Building extends Model implements Cloneable {

    protected int id;
    protected String name;
    protected String address;
    protected double surface;

    protected List<Energy> energyTypes;
    protected List<Alert>  alerts;

    private static final double COST_WARNING_THRESHOLD  = 1_000.0;
    private static final double COST_CRITICAL_THRESHOLD = 5_000.0;

    /** Constructeur principal — id auto-incrémenté par le DAO. */
    public Building(String name, String address, double surface) {
        this.name        = name;
        this.address     = address;
        this.surface     = surface;
        this.energyTypes = new ArrayList<>();
        this.alerts      = new ArrayList<>();
    }

    // ── Gestion des énergies ──────────────────────────────────

    public void addEnergy(Energy e) {
        if (e != null) {
            energyTypes.add(e);
            checkAndGenerateAlerts();
        }
    }

    public void removeEnergy(Energy e) {
        energyTypes.remove(e);
    }

    public List<Energy> getEnergyTypes() {
        return new ArrayList<>(energyTypes);
    }

    // ── Calculs métier ────────────────────────────────────────

    public double getTotalConsumption() {
        return energyTypes.stream().mapToDouble(Energy::getQuantity).sum();
    }

    public double getEstimatedCost() {
        return energyTypes.stream().mapToDouble(Energy::getEstimatedCost).sum();
    }

    // ── Gestion des alertes ───────────────────────────────────

    public List<Alert> getAlerts() {
        return new ArrayList<>(alerts);
    }

    public void addAlert(Alert alert) {
        if (alert != null) alerts.add(alert);
    }

    private void checkAndGenerateAlerts() {
        double cost = getEstimatedCost();
        if (cost >= COST_CRITICAL_THRESHOLD) {
            alerts.add(new Alert(
                    "Coût critique : " + String.format("%.2f", cost) + " € – " + name,
                    AlertLevel.CRITICAL
            ));
        } else if (cost >= COST_WARNING_THRESHOLD) {
            alerts.add(new Alert(
                    "Coût élevé : " + String.format("%.2f", cost) + " € – " + name,
                    AlertLevel.WARNING
            ));
        }
    }

    // ── Clone ─────────────────────────────────────────────────

    @Override
    public Building clone() {
        try {
            Building cloned      = (Building) super.clone();
            cloned.energyTypes   = new ArrayList<>(this.energyTypes);
            cloned.alerts        = new ArrayList<>(this.alerts);
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Clonage impossible pour " + getClass().getSimpleName(), e);
        }
    }

    // ── Getters / Setters ─────────────────────────────────────

    public int getId()                   { return id; }
    public void setId(int id)            { this.id = id; }

    public String getName()              { return name; }
    public void setName(String name)     { this.name = name; }

    public String getAddress()           { return address; }
    public void setAddress(String addr)  { this.address = addr; }

    public double getSurface()           { return surface; }
    public void setSurface(double s)     { this.surface = s; }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "{id=" + id + ", name='" + name + "', address='" + address
                + "', surface=" + surface
                + ", totalCost=" + String.format("%.2f", getEstimatedCost()) + "€}";
    }

    @Override
    public String toCSV() {
        return "";
    }

    public static Building fromCSV(String[] fields) {
        Building building = new Building(fields[1], fields[2], Double.parseDouble(fields[3]));
        building.setId(Integer.parseInt(fields[0]));
        return building;
    }
}

