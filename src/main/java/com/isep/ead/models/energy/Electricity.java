package com.isep.ead.models.energy;

import com.isep.ead.models.IModel;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Consommation d'électricité.
 * Les heures creuses bénéficient d'un tarif réduit de 30 %.
 */
public class Electricity extends Energy implements IModel<Electricity> {

    private boolean isOffPeak;
    private static final double OFF_PEAK_DISCOUNT = 0.30;

    public Electricity() {
        super(LocalDate.now(), 0, 0);
    }

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
        return id + "," + buildingId + "," + date + "," + time + "," + quantity + "," + pricePerUnit + "," + isOffPeak;
    }

    @Override
    public Electricity fromCSV(String[] fields) {
        // Old format (6 fields): id,buildingId,date,quantity,pricePerUnit,isOffPeak
        // New format (7 fields): id,buildingId,date,time,quantity,pricePerUnit,isOffPeak
        Electricity e;
        if (fields.length >= 7) {
            e = new Electricity(
                    LocalDate.parse(fields[2]),
                    Double.parseDouble(fields[4]),
                    Double.parseDouble(fields[5]),
                    Boolean.parseBoolean(fields[6])
            );
            try { e.setTime(LocalTime.parse(fields[3])); } catch (Exception ignored) {}
        } else {
            e = new Electricity(
                    LocalDate.parse(fields[2]),
                    Double.parseDouble(fields[3]),
                    Double.parseDouble(fields[4]),
                    Boolean.parseBoolean(fields[5])
            );
        }
        e.setId(Integer.parseInt(fields[0]));
        e.setBuildingId(Integer.parseInt(fields[1]));
        return e;
    }

    public boolean isOffPeak() { return isOffPeak; }
    public void setOffPeak(boolean offPeak) { isOffPeak = offPeak; }
}
