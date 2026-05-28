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
    protected int organizationId;

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

    public int getOrganizationId()              { return organizationId; }
    public void setOrganizationId(int orgId)    { this.organizationId = orgId; }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "{id=" + id + ", name='" + name + "', address='" + address
                + "', surface=" + surface
                + ", totalCost=" + String.format("%.2f", getEstimatedCost()) + "€}";
    }

    @Override
    public String toCSV() {
        return String.format("%s,%s,%s,%s,%s,%s", this.id, getClass().getSimpleName(), this.name, this.address, this.surface, this.organizationId);
    }

    private static final java.util.Set<String> KNOWN_TYPES =
        java.util.Set.of("Building", "House", "Appartment", "Office", "Shop");

    @Override
    public Building fromCSV(String[] fields) {
        // Nouveau format: 0=id, 1=type, 2=name, 3=address, 4=surface, 5=organizationId
        // Ancien format:  0=id, 1=name, 2=address, 3=surface, 4=organizationId
        boolean newFormat = fields.length > 1 && KNOWN_TYPES.contains(fields[1]);
        int offset = newFormat ? 2 : 1;

        Building building = newFormat ? switch (fields[1]) {
            case "House"      -> new House();
            case "Appartment" -> new Appartment();
            case "Office"     -> new Office();
            case "Shop"       -> new Shop();
            default           -> new Building();
        } : new Building();

        try { building.setId(Integer.parseInt(fields[0])); } catch (Exception ignored) {}
        if (fields.length > offset)     building.setName(fields[offset]);
        if (fields.length > offset + 1) building.setAddress(fields[offset + 1]);
        if (fields.length > offset + 2) {
            try { building.setSurface(Double.parseDouble(fields[offset + 2])); } catch (Exception ignored) {}
        }
        if (fields.length > offset + 3) {
            try { building.setOrganizationId(Integer.parseInt(fields[offset + 3])); } catch (Exception ignored) {}
        }
        return building;
    }
}
