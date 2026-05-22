package com.isep.ead.models.organization;
import com.isep.ead.models.Model;
import com.isep.ead.models.building.Building;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
public class Organization extends Model {
    protected int id;
    private String name;
    private List<Building> buildingList;
    public Organization(String name) {
        this.name = name;
        this.buildingList = new ArrayList<>();
    }
    public void addBuilding(Building building) {
        if (building != null && !buildingList.contains(building)) buildingList.add(building);
    }
    public void removeBuilding(Building building) { buildingList.remove(building); }
    public List<Building> getBuildings()  { return new ArrayList<>(buildingList); }
    public int getNumberOfBuildings()     { return buildingList.size(); }
    public double getTotalConsumption()   { return buildingList.stream().mapToDouble(Building::getTotalConsumption).sum(); }
    public double getEstimatedCost()      { return buildingList.stream().mapToDouble(Building::getEstimatedCost).sum(); }
    public Building getMostConsumingBuilding() {
        return buildingList.stream().max(Comparator.comparingDouble(Building::getTotalConsumption)).orElse(null);
    }
    public double getDailyConsumption()   { return getTotalConsumption() / 365.0; }
    public double getMonthlyConsumption() { return getTotalConsumption() / 12.0; }
    public double getAnnualConsumption()  { return getTotalConsumption(); }
    @Override
    public String toCSV() { return id + "," + name; }
    public static Organization fromCSV(String csv) {
        String[] parts = csv.split(",");
        Organization org = new Organization(parts[1]);
        org.setId(Integer.parseInt(parts[0]));
        return org;
    }
    public int getId()             { return id; }
    public void setId(int id)      { this.id = id; }
    public String getName()        { return name; }
    public void setName(String n)  { this.name = n; }
    @Override
    public String toString() {
        return "Organization{id=" + id + ", name=" + name + ", buildings=" + buildingList.size() + ", cost=" + String.format("%.2f", getEstimatedCost()) + "}";
    }
}
