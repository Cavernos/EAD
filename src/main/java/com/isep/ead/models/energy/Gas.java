package com.isep.ead.models.energy;

import java.time.LocalDate;


public class Gas extends Energy {


    public Gas(LocalDate date, double quantity, double pricePerUnit) {
        super(date, quantity, pricePerUnit);
    }

    @Override
    public double getEstimatedCost() {
        return super.getEstimatedCost();
    }

    @Override
    public String toCSV() {

        return id + "," + date + "," + quantity + "," + pricePerUnit;
    }
    
    @Override
    public Gas fromCSV(String[] fields) {
        Gas gas = new Gas(
                LocalDate.parse(fields[1]),
                Double.parseDouble(fields[2]),
                Double.parseDouble(fields[3])
        );
        gas.setId(Integer.parseInt(fields[0]));
        return gas;
    }
}

