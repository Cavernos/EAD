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

    public Shop fromCSV(String[] fields) {
        Shop shop = new Shop(fields[1], fields[2], Double.parseDouble(fields[3]), fields[4]);
        shop.setId(Integer.parseInt(fields[0]));
        return shop;
    }


    public String getActivitySector() {
        return activitySector;
    }

    public void setActivitySector(String s) {
        this.activitySector = s;
    }
}

