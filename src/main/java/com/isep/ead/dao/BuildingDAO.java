package com.isep.ead.dao;

import com.isep.ead.models.buildings.Building;
import com.isep.ead.utils.CSVHandler;

public class BuildingDAO implements IDAO{
    public static int idSequence = 1;
    private final CSVHandler csvHandler;

    public BuildingDAO() {
        this.csvHandler = new CSVHandler("buildings.csv");
        idSequence = this.getLastId();
    }
    @Override
    public Building create(Object object) {
        return this.create((Building) object);
    }

    public Building create(Building building) {
        building.setId(idSequence++);
        this.csvHandler.write(building.toCSV());
        return building;
    }

    @Override
    public Building getById(int id) {
        String[] rows = this.csvHandler.read();
        for (String row : rows) {
            Building building = Building.fromCSV(row);
            if (building.getId() == id) {
                return building;
            }
        }
        return null;
    }
    private int getLastId() {
        String row = this.csvHandler.getLastRow();
        if (!row.contains(",")) {
            return 1;
        }
        return Integer.parseInt(row.split(",")[0]);
    }
    @Override
    public void remove(Object object) {
        this.remove((Building) object);
    }

    public void remove(Building building) {
        if (building != null) {
            this.csvHandler.remove(building.getId());
        }
    }

    @Override
    public Object update(Object object) {
        return this.update((Building) object);
    }
    public Building update(Building building) {
        return Building.fromCSV(this.csvHandler.update(building.getId(), building.toCSV()));
    }
}
