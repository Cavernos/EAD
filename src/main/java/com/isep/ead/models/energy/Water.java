package com.isep.ead.models.energy;

import java.time.LocalDate;


public class Water extends Energy {

    private boolean isHotWater;

    private static final double HOT_WATER_SURCHARGE = 0.20;

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
        return id + "," + date + "," + quantity + "," + pricePerUnit + "," + isHotWater;
    }

    
    @Override
    public Water fromCSV(String[] fields) {
        Water water = new Water(
                LocalDate.parse(fields[1]),
                Double.parseDouble(fields[2]),
                Double.parseDouble(fields[3]),
                Boolean.parseBoolean(fields[4])
        );
        water.setId(Integer.parseInt(fields[0]));
        return water;
    }
    public boolean isHotWater() {
        return isHotWater;
    }

    public void setHotWater(boolean hotWater) {
        isHotWater = hotWater;
    }
}

