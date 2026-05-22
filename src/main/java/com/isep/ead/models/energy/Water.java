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
        double baseCost = super.getEstimatedCost();
        return isHotWater ? baseCost * (1 + HOT_WATER_SURCHARGE) : baseCost;
    }
    @Override
    public String toCSV() {
        return id + "," + date + "," + quantity + "," + pricePerUnit + "," + isHotWater;
    }
    public static Water fromCSV(String csv) {
        String[] parts = csv.split(",");
        Water water = new Water(LocalDate.parse(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3]), Boolean.parseBoolean(parts[4]));
        water.setId(Integer.parseInt(parts[0]));
        return water;
    }
    public boolean isHotWater()                 { return isHotWater; }
    public void setHotWater(boolean isHotWater) { this.isHotWater = isHotWater; }
}
