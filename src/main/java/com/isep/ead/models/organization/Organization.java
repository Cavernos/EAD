package com.isep.ead.models.organization;
import com.isep.ead.models.Model;
import com.isep.ead.models.building.Building;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
public class Organization extends Model {
    protected int id;
    private String name;
    private List<Building> buildings;
    private int numberOfBuildings;
    public Organization(String name) {
        this.name = name;
        this.buildings = new ArrayList<>();
        this.numberOfBuildings = 0;
    }
    public void addBuilding(Building building) {
        if (building != null && !buildings.contains(building)) {
            buildings.add(building);
            numberOfBuildings++;
        }
    }
    public void removeBuilding(Building building) {
        if (buildings.remove(building)) numberOfBuildings--;
    }
    public List<Building> getBuildings()      { return new ArrayList<>(buildings); }
    public int getNumberOfBuildings()         { return numberOfBuildings; }
    public double getTotalConsumption()       { return buildings.stream().mapToDouble(Building::getTotalConsumption).sum(); }
    public double getEstimatedCost()          { return buildings.stream().mapToDouble(Building::getEstimatedCost).sum(); }
    public Building getMostConsumingBuilding() {
        return buildings.stream().max(Comparator.comparingDouble(Building::getTotalConsumption)).orElse(null);
    }
    public double getDailyConsumption()       { return getTotalConsumption() / 365.0; }
    public double getMonthlyConsumption()     { return getTotalConsumption() / 12.0; }
    public double getAnnualConsumption()      { return getTotalConsumption(); }
    @Override
    public String toCSV() { return id + "," + name; }
    public static Organization fromCSV(String csv) {
        String[] parts = csv.split(",");
        Organization organization = new Organization(parts[1]);
        organization.setId(Integer.parseInt(parts[0]));
        return organization;
    }
    public int getId()              { return id; }
    public void setId(int id)       { this.id = id; }
    public String getName()         { return name; }
    public void setName(String name){ this.name = name; }
    @Override
    public String toString() {
        return "Organization{id=" + id + ", name=" + name + ", buildings=" + numberOfBuildings + "}";
    }
}
