package com.isep.ead.models.buildings;

import com.isep.ead.models.IModel;

public class Building implements IModel {
    private int id;

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public Building(String name) {
        this.name = name;
    }
    public static Building fromCSV(String csv) {
        String[] fields = csv.split(",");
        Building building = new Building(fields[1]);
        building.setId(Integer.parseInt(fields[0]));
        return building;
    }

    @Override
    public String toCSV() {
        return String.format("%s,%s", this.id, this.name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
