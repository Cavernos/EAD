package com.isep.ead.models.energy;

import com.isep.ead.models.IModel;
import java.time.LocalDate;
import java.time.LocalTime;


public class Gas extends Energy implements IModel<Gas> {

    public Gas() {
        super(LocalDate.now(), 0, 0);
    }

    public Gas(LocalDate date, double quantity, double pricePerUnit) {
        super(date, quantity, pricePerUnit);
    }

    @Override
    public double getEstimatedCost() {
        return super.getEstimatedCost();
    }

    @Override
    public String toCSV() {
        return id + "," + buildingId + "," + date + "," + time + "," + quantity + "," + pricePerUnit;
    }

    @Override
    public Gas fromCSV(String[] fields) {
        // Old format (5 fields): id,buildingId,date,quantity,pricePerUnit
        // New format (6 fields): id,buildingId,date,time,quantity,pricePerUnit
        Gas gas;
        if (fields.length >= 6) {
            gas = new Gas(
                    LocalDate.parse(fields[2]),
                    Double.parseDouble(fields[4]),
                    Double.parseDouble(fields[5])
            );
            try { gas.setTime(LocalTime.parse(fields[3])); } catch (Exception ignored) {}
        } else {
            gas = new Gas(
                    LocalDate.parse(fields[2]),
                    Double.parseDouble(fields[3]),
                    Double.parseDouble(fields[4])
            );
        }
        gas.setId(Integer.parseInt(fields[0]));
        gas.setBuildingId(Integer.parseInt(fields[1]));
        return gas;
    }
}
