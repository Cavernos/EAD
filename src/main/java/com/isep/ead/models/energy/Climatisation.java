package com.isep.ead.models.energy;

import com.isep.ead.models.IModel;
import java.time.LocalDate;
import java.time.LocalTime;


public class Climatisation extends Energy implements IModel<Climatisation> {

    private double targetTemperature;

    private static final double REFERENCE_TEMP = 20.0;
    private static final double TEMP_FACTOR = 0.05;

    public Climatisation() {
        super(LocalDate.now(), 0, 0);
    }

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
        return id + "," + buildingId + "," + date + "," + time + "," + quantity + "," + pricePerUnit + "," + targetTemperature;
    }

    @Override
    public Climatisation fromCSV(String[] fields) {
        // Old format (6 fields): id,buildingId,date,quantity,pricePerUnit,targetTemperature
        // New format (7 fields): id,buildingId,date,time,quantity,pricePerUnit,targetTemperature
        Climatisation climatisation;
        if (fields.length >= 7) {
            climatisation = new Climatisation(
                    LocalDate.parse(fields[2]),
                    Double.parseDouble(fields[4]),
                    Double.parseDouble(fields[5]),
                    Double.parseDouble(fields[6])
            );
            try { climatisation.setTime(LocalTime.parse(fields[3])); } catch (Exception ignored) {}
        } else {
            climatisation = new Climatisation(
                    LocalDate.parse(fields[2]),
                    Double.parseDouble(fields[3]),
                    Double.parseDouble(fields[4]),
                    Double.parseDouble(fields[5])
            );
        }
        climatisation.setId(Integer.parseInt(fields[0]));
        climatisation.setBuildingId(Integer.parseInt(fields[1]));
        return climatisation;
    }

    public double getTargetTemperature() {
        return targetTemperature;
    }

    public void setTargetTemperature(double t) {
        this.targetTemperature = t;
    }
}
