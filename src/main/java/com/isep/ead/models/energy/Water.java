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
    public static Water fromCSV(String csv) {
        String[] parts = csv.split(",");
        Water w = new Water(LocalDate.parse(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3]), Boolean.parseBoolean(parts[4]));
        w.setId(Integer.parseInt(parts[0]));
        return w;
    }
    public boolean isHotWater()                 { return isHotWater; }
    public void setHotWater(boolean isHotWater)  { this.isHotWater = isHotWater; }
}
