package com.isep.ead.models.energy;

import com.isep.ead.models.IModel;
import java.time.LocalDate;


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
        return id + "," + buildingId + "," + date + "," + quantity + "," + pricePerUnit;
    }

    @Override
    public Gas fromCSV(String[] fields) {
        Gas gas = new Gas(
                LocalDate.parse(fields[2]),
                Double.parseDouble(fields[3]),
                Double.parseDouble(fields[4])
        );
        gas.setId(Integer.parseInt(fields[0]));
        gas.setBuildingId(Integer.parseInt(fields[1]));
        return gas;
    }
}
