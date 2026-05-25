package com.isep.ead.models.energy;

import java.time.LocalDate;

public class Climatisation extends Energy {
    private double targetTemperature;
    private static final double REFERENCE_TEMP = 20.0;
    private static final double TEMP_COST_FACTOR = 0.05;

    public Climatisation(LocalDate date, double quantity, double pricePerUnit, double targetTemperature) {
        super(date, quantity, pricePerUnit);
        this.targetTemperature = targetTemperature;
    }

    @Override
    public double getEstimatedCost() {
        double delta = Math.abs(targetTemperature - REFERENCE_TEMP);
        return super.getEstimatedCost() * (1.0 + delta * TEMP_COST_FACTOR);
    }

    @Override
    public String toCSV() {
        return id + "," + date + "," + quantity + "," + pricePerUnit + "," + targetTemperature;
    }

    public static Climatisation fromCSV(String csv) {
        String[] parts = csv.split(",");
        Climatisation climatisation = new Climatisation(LocalDate.parse(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3]), Double.parseDouble(parts[4]));
        climatisation.setId(Integer.parseInt(parts[0]));
        return climatisation;
    }

    public double getTargetTemperature() {
        return targetTemperature;
    }

    public void setTargetTemperature(double targetTemperature) {
        this.targetTemperature = targetTemperature;
    }
}
