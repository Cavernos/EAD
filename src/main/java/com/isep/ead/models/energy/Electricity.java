package com.isep.ead.models.energy;

import java.time.LocalDate;

/**
 * Consommation d'électricité.
 * Les heures creuses bénéficient d'un tarif réduit de 30 %.
 */
public class Electricity extends Energy {

    private boolean isOffPeak;

    private static final double OFF_PEAK_DISCOUNT = 0.30;

    public Electricity(LocalDate date, double quantity, double pricePerUnit, boolean isOffPeak) {
        super(date, quantity, pricePerUnit);
        this.isOffPeak = isOffPeak;
    }



    @Override
    public double getEstimatedCost() {
        double base = super.getEstimatedCost();
        return isOffPeak ? base * (1 - OFF_PEAK_DISCOUNT) : base;
    }

    @Override
    public String toCSV() {
        return id + "," + date + "," + quantity + "," + pricePerUnit + "," + isOffPeak;
    }


    @Override
    public Electricity fromCSV(String[] fields) {
        Electricity e = new Electricity(
                LocalDate.parse(fields[1]),
                Double.parseDouble(fields[2]),
                Double.parseDouble(fields[3]),
                Boolean.parseBoolean(fields[4])
        );
        e.setId(Integer.parseInt(fields[0]));
        return e;
    }

    public boolean isOffPeak() {
        return isOffPeak;
    }

    public void setOffPeak(boolean offPeak) {
        isOffPeak = offPeak;
    }
}

