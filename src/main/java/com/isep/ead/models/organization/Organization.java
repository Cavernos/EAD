package com.isep.ead.models.organization;

import com.isep.ead.models.IModel;
import com.isep.ead.models.Model;
import com.isep.ead.models.building.Building;

import java.util.ArrayList;
import java.util.List;

public class Organization extends Model<Organization>{


    protected int id;
    private String name;
    private String owner;
    private final List<Building> buildings = new ArrayList<>();

    public Organization() {

    }

    public Organization(String name) {
        this.name = name;
    }

    public void addBuilding(Building building) {
        if (building != null && !buildings.contains(building)) {
            buildings.add(building);
        }
    }

    public void removeBuilding(Building building) {
        buildings.remove(building);
    }


    public List<Building> getBuildings() {
        return new ArrayList<>(buildings);
    }

    public int countBuildings() {
        return buildings.size();
    }

    public double getTotalConsumption() {
        double consumption = 0.0;
        for (Building building: this.buildings) {
            consumption += building.getTotalConsumption();
        }
        return consumption;
    }

    public double getEstimatedCost() {
        double costs = 0.0;
        for (Building building: this.buildings) {
            costs += building.getEstimatedCost();
        }
        return costs;
    }

    public Building getMostConsumingBuilding() {
        Building mostConsumingBuilding = new Building();
        for (Building building : this.buildings) {
            if (building.getTotalConsumption() > mostConsumingBuilding.getTotalConsumption()) {
                mostConsumingBuilding = building;
            }
        }
        return mostConsumingBuilding;
    }

    public double getDailyConsumption() {
        return this.getTotalConsumption() / 365.0;
    }

    public double getMonthlyConsumption() {
        return this.getTotalConsumption() / 12.0;
    }

    public double getAnnualConsumption() {
        return this.getTotalConsumption();
    }

    @Override
    public String toCSV() {
        return id + "," + name + "," + owner;
    }

    @Override
    public Organization fromCSV(String[] fields) {
        Organization organization = new Organization(fields[1]);
        organization.setId(Integer.parseInt(fields[0]));
        if (fields.length > 2 && fields[2] != null && !fields[2].isBlank()) {
            organization.setOwner(fields[2]);
        }
        return organization;
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


    @Override
    public String toString() {
        return "Organization{id=" + id + ", name='" + name + ", owner='" + owner +"', buildings=" + buildings.size() + ", totalCost=" + String.format("%.2f", getEstimatedCost()) + "EUR}";
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
