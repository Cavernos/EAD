package com.isep.ead.models.building;

public class Shop extends Building {

    private String activitySector;

    public Shop(String name, String address, double surface, String activitySector) {
        super(name, address, surface);
        this.activitySector = activitySector;
    }

    public Shop() {

    }


    @Override
    public String toCSV() {
        return id + ",Shop," + name + "," + address + "," + surface + "," + organizationId + "," + activitySector;
    }

    @Override
    public Shop fromCSV(String[] fields) {
        // format: id,Shop,name,address,surface,organizationId[,activitySector]
        Shop shop = new Shop();
        shop.setId(Integer.parseInt(fields[0]));
        shop.setName(fields[2]);
        shop.setAddress(fields[3]);
        try { shop.setSurface(Double.parseDouble(fields[4])); } catch (Exception ignored) {}
        if (fields.length > 5) try { shop.setOrganizationId(Integer.parseInt(fields[5])); } catch (Exception ignored) {}
        if (fields.length > 6) shop.setActivitySector(fields[6]);
        return shop;
    }


    public String getActivitySector() {
        return activitySector;
    }

    public void setActivitySector(String s) {
        this.activitySector = s;
    }
}

