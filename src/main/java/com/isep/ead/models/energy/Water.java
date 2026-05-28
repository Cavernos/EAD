package com.isep.ead.models.energy;

import com.isep.ead.models.IModel;
import java.time.LocalDate;


public class Water extends Energy implements IModel<Water> {

    private boolean isHotWater;
    private static final double HOT_WATER_SURCHARGE = 0.20;

    public Water() {
        super(LocalDate.now(), 0, 0);
    }

    public Water(LocalDate date, double quantity, double pricePerUnit, boolean isHotWater) {
        super(date, quantity, pricePerUnit);
        this.isHotWater = isHotWater;
    }

    @Override
    public double getEstimatedCost() {
        double base = super.getEstimatedCost();
        return isHotWater ? base * (1 + HOT_WATER_SURCHARGE) : base;
    }

    @Override
    public String toCSV() {
        return id + "," + buildingId + "," + date + "," + quantity + "," + pricePerUnit + "," + isHotWater;
    }

    @Override
    public Water fromCSV(String[] fields) {
        Water water = new Water(
                LocalDate.parse(fields[2]),
                Double.parseDouble(fields[3]),
                Double.parseDouble(fields[4]),
                Boolean.parseBoolean(fields[5])
        );
        water.setId(Integer.parseInt(fields[0]));
        water.setBuildingId(Integer.parseInt(fields[1]));
        return water;
    }

    public boolean isHotWater() { return isHotWater; }
    public void setHotWater(boolean hotWater) { isHotWater = hotWater; }
}
