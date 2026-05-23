package com.isep.ead.models.building;

import com.isep.ead.models.IModel;
import com.isep.ead.models.alert.Alert;
import com.isep.ead.models.alert.AlertLevel;
import com.isep.ead.models.energy.Energy;

import java.util.ArrayList;
import java.util.List;


public class Building implements IModel<Building> {

    protected int id;
    protected String name;
    protected String address;
    protected double surface;

    protected List<Energy> energyTypes;
    protected List<Alert>  alerts;

    private static final double COST_WARNING_THRESHOLD  = 1_000.0;
    private static final double COST_CRITICAL_THRESHOLD = 5_000.0;

    public Building() {

    }
    public Building(String name, String address, double surface) {
        this.name        = name;
        this.address     = address;
        this.surface     = surface;
        this.energyTypes = new ArrayList<>();
        this.alerts      = new ArrayList<>();
    }



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



    public double getTotalConsumption() {
        return energyTypes.stream().mapToDouble(Energy::getQuantity).sum();
    }

    public double getEstimatedCost() {
        return energyTypes.stream().mapToDouble(Energy::getEstimatedCost).sum();
    }



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
        return String.format("%s,%s,%s,%s", this.id,this.name, this.address, this.surface);
    }

    @Override
    public Building fromCSV(String[] fields) {
        Building building = new Building(fields[1], fields[2], Double.parseDouble(fields[3]));
        building.setId(Integer.parseInt(fields[0]));
        return building;
    }
}

