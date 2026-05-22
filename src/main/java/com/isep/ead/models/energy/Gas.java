package com.isep.ead.models.energy;
import java.time.LocalDate;
public class Gas extends Energy {
    public Gas(LocalDate date, double quantity, double pricePerUnit) {
        super(date, quantity, pricePerUnit);
    }
    @Override
    public String toCSV() {
        return id + "," + date + "," + quantity + "," + pricePerUnit;
    }
    public static Gas fromCSV(String csv) {
        String[] parts = csv.split(",");
        Gas g = new Gas(LocalDate.parse(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3]));
        g.setId(Integer.parseInt(parts[0]));
        return g;
    }
}
