package com.isep.ead.models.building;
public class Shop extends Building {
    private String activitySector;
    public Shop(String name, String address, double surface, String activitySector) {
        super(name, address, surface);
        this.activitySector = activitySector;
    }
    @Override
    public String toCSV() {
        return id + "," + name + "," + address + "," + surface + "," + activitySector;
    }
    public static Shop fromCSV(String csv) {
        String[] parts = csv.split(",");
        Shop shop = new Shop(parts[1], parts[2], Double.parseDouble(parts[3]), parts[4]);
        shop.setId(Integer.parseInt(parts[0]));
        return shop;
    }
    public String getActivitySector()                    { return activitySector; }
    public void setActivitySector(String activitySector) { this.activitySector = activitySector; }
}
