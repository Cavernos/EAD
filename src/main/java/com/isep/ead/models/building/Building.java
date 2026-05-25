package com.isep.ead.models.building;

import com.isep.ead.models.Model;
import com.isep.ead.models.alert.Alert;
import com.isep.ead.models.energy.Energy;

import java.util.ArrayList;
import java.util.List;

public class Building extends Model {
    protected int id;
    protected String name;
    protected String address;
    protected double surface;
    protected List<Energy> energyTypes;
    protected List<Alert> alerts;

    public Building(String name, String address, double surface) {
        this.name = name;
        this.address = address;
        this.surface = surface;
        this.energyTypes = new ArrayList<>();
        this.alerts = new ArrayList<>();
    }

    public void addEnergy(Energy energy) {
        if (energy != null) energyTypes.add(energy);
    }

    public void removeEnergy(Energy energy) {
        energyTypes.remove(energy);
    }

    public List<Energy> getEnergyTypes() {
        return new ArrayList<>(energyTypes);
    }

    public List<Alert> getAlerts() {
        return new ArrayList<>(alerts);
    }

    public double getTotalConsumption() {
        return energyTypes.stream().mapToDouble(Energy::getQuantity).sum();
    }

    public double getEstimatedCost() {
        return energyTypes.stream().mapToDouble(Energy::getEstimatedCost).sum();
    }

    @Override
    public String toCSV() {
        return id + "," + name + "," + address + "," + surface;
    }

    public static Building fromCSV(String csv) {
        String[] parts = csv.split(",");
        Building building = new Building(parts[1], parts[2], Double.parseDouble(parts[3]));
        building.setId(Integer.parseInt(parts[0]));
        return building;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getSurface() {
        return surface;
    }

    public void setSurface(double surface) {
        this.surface = surface;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{id=" + id + ", name=" + name + ", address=" + address + ", surface=" + surface + "}";
    }
}
