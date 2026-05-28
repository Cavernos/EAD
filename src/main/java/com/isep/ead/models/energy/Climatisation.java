package com.isep.ead.models.energy;

import java.time.LocalDate;


public class Climatisation extends Energy {

    private double targetTemperature;

    private static final double REFERENCE_TEMP = 20.0;
    private static final double TEMP_FACTOR = 0.05;


    public Climatisation(LocalDate date, double quantity, double pricePerUnit, double targetTemperature) {
        super(date, quantity, pricePerUnit);
        this.targetTemperature = targetTemperature;
    }



    @Override
    public double getEstimatedCost() {
        double delta = Math.abs(targetTemperature - REFERENCE_TEMP);
        double surcharge = 1.0 + (delta * TEMP_FACTOR);
        return super.getEstimatedCost() * surcharge;
    }


    @Override
    public String toCSV() {
        return id + "," + date + "," + quantity + "," + pricePerUnit + "," + targetTemperature;
    }

    @Override
    public Climatisation fromCSV(String[] fields) {
        Climatisation climatisation = new Climatisation(
                LocalDate.parse(fields[1]),
                Double.parseDouble(fields[2]),
                Double.parseDouble(fields[3]),
                Double.parseDouble(fields[4])
        );
        climatisation.setId(Integer.parseInt(fields[0]));
        return climatisation;
    }

    public double getTargetTemperature() {
        return targetTemperature;
    }

    public void setTargetTemperature(double t) {
        this.targetTemperature = t;
    }
}

