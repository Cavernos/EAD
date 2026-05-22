package com.isep.ead.models.energy;
import java.time.LocalDate;
public class Electricity extends Energy {
    private boolean isOffPeak;
    private static final double OFF_PEAK_DISCOUNT = 0.30;
    public Electricity(LocalDate date, double quantity, double pricePerUnit, boolean isOffPeak) {
        super(date, quantity, pricePerUnit);
        this.isOffPeak = isOffPeak;
    }
    @Override
    public double getEstimatedCost() {
        double baseCost = super.getEstimatedCost();
        return isOffPeak ? baseCost * (1 - OFF_PEAK_DISCOUNT) : baseCost;
    }
    @Override
    public String toCSV() {
        return id + "," + date + "," + quantity + "," + pricePerUnit + "," + isOffPeak;
    }
    public static Electricity fromCSV(String csv) {
        String[] parts = csv.split(",");
        Electricity electricity = new Electricity(LocalDate.parse(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3]), Boolean.parseBoolean(parts[4]));
        electricity.setId(Integer.parseInt(parts[0]));
        return electricity;
    }
    public boolean isOffPeak()                { return isOffPeak; }
    public void setOffPeak(boolean isOffPeak) { this.isOffPeak = isOffPeak; }
}
