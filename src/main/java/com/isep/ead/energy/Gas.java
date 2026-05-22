package com.isep.ead.energy;

import java.time.LocalDate;

/**
 * Consommation de gaz.
 * Calcul de coût standard : quantité × prix unitaire.
 */
public class Gas extends Energy {

    public Gas(int id, LocalDate date, double quantity, double pricePerUnit) {
        super(id, date, quantity, pricePerUnit);
    }

    // ── Méthodes métier ───────────────────────────────────────

    @Override
    public double getEstimatedCost() {
        return super.getEstimatedCost();
    }

    // ── CSV ───────────────────────────────────────────────────

    @Override
    public String toCSV() {
        return "GAS;" + id + ";" + date + ";" + quantity + ";" + pricePerUnit;
    }

    public static Gas fromCSV(String csv) {
        String[] parts = csv.split(";");
        return new Gas(
                Integer.parseInt(parts[1]),
                LocalDate.parse(parts[2]),
                Double.parseDouble(parts[3]),
                Double.parseDouble(parts[4])
        );
    }
}

