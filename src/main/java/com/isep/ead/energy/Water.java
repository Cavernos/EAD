package com.isep.ead.energy;

import java.time.LocalDate;

/**
 * Consommation d'eau.
 * L'eau chaude est plus coûteuse (surcoût de 20 %).
 */
public class Water extends Energy {

    private boolean isHotWater;

    private static final double HOT_WATER_SURCHARGE = 0.20;

    public Water(int id, LocalDate date, double quantity, double pricePerUnit, boolean isHotWater) {
        super(id, date, quantity, pricePerUnit);
        this.isHotWater = isHotWater;
    }

    // ── Méthodes métier ───────────────────────────────────────

    /** Coût avec surcharge eau chaude si applicable. */
    @Override
    public double getEstimatedCost() {
        double baseCost = super.getEstimatedCost();
        return isHotWater ? baseCost * (1 + HOT_WATER_SURCHARGE) : baseCost;
    }

    // ── CSV ───────────────────────────────────────────────────

    @Override
    public String toCSV() {
        return "WATER;" + id + ";" + date + ";" + quantity + ";" + pricePerUnit + ";" + isHotWater;
    }

    public static Water fromCSV(String csv) {
        String[] parts = csv.split(";");
        return new Water(
                Integer.parseInt(parts[1]),
                LocalDate.parse(parts[2]),
                Double.parseDouble(parts[3]),
                Double.parseDouble(parts[4]),
                Boolean.parseBoolean(parts[5])
        );
    }

    // ── Getters / Setters ─────────────────────────────────────

    public boolean isHotWater() {
        return isHotWater;
    }

    public void setHotWater(boolean hotWater) {
        isHotWater = hotWater;
    }
}

