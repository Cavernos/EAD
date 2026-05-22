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
    public Organization(String name) {
        this.name = name;
        this.buildings = new ArrayList<>();
    }
    public void addBuilding(Building b) { if (b != null && !buildings.contains(b)) buildings.add(b); }
    public void removeBuilding(Building b) { buildings.remove(b); }
    public List<Building> getBuildings() { return new ArrayList<>(buildings); }
    public int getNumberOfBuildings() { return buildings.size(); }
    public double getTotalConsumption() { return buildings.stream().mapToDouble(Building::getTotalConsumption).sum(); }
    public double getEstimatedCost() { return buildings.stream().mapToDouble(Building::getEstimatedCost).sum(); }
    public Building getMostConsumingBuilding() {
        return buildings.stream().max(Comparator.comparingDouble(Building::getTotalConsumption)).orElse(null);
    }
    public double getDailyConsumption()   { return getTotalConsumption() / 365.0; }
    public double getMonthlyConsumption() { return getTotalConsumption() / 12.0; }
    public double getAnnualConsumption()  { return getTotalConsumption(); }
    /** Format : id,name */
    @Override
    public String toCSV() { return id + "," + name; }
    public static Organization fromCSV(String csv) {
        String[] p = csv.split(",");
        Organization o = new Organization(p[1]);
        o.setId(Integer.parseInt(p[0]));
        return o;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String n) { this.name = n; }
    @Override
    public String toString() {
        return "Organization{id=" + id + ", name='" + name + "', buildings=" + buildings.size() + ", totalCost=" + String.format("%.2f", getEstimatedCost()) + "EUR}";
    }
}
