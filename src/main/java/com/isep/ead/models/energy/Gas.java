package com.isep.ead.models.energy;

import java.time.LocalDate;

/**
 * Consommation de gaz.
 * Calcul de coût standard : quantité × prix unitaire.
 */
public class Gas extends Energy {

    /** Constructeur principal — id auto-incrémenté par le DAO. */
    public Gas(LocalDate date, double quantity, double pricePerUnit) {
        super(date, quantity, pricePerUnit);
    }

    // ── Méthodes métier ───────────────────────────────────────

    @Override
    public double getEstimatedCost() {
        return super.getEstimatedCost();
    }

    // ── CSV ───────────────────────────────────────────────────

    /** Format : id,date,quantity,pricePerUnit */
    @Override
    public String toCSV() {
        return id + "," + date + "," + quantity + "," + pricePerUnit;
    }

    public static Gas fromCSV(String csv) {
        String[] p = csv.split(",");
        Gas g = new Gas(
                LocalDate.parse(p[1]),
                Double.parseDouble(p[2]),
                Double.parseDouble(p[3])
        );
        g.setId(Integer.parseInt(p[0]));
        return g;
    }
}

