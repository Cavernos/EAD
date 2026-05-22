package com.isep.ead.models.energy;

import java.time.LocalDate;

/**
 * Consommation d'eau.
 * L'eau chaude applique un surcoût de 20 %.
 */
public class Water extends Energy {

    private boolean isHotWater;

    private static final double HOT_WATER_SURCHARGE = 0.20;

    /** Constructeur principal — id auto-incrémenté par le DAO. */
    public Water(LocalDate date, double quantity, double pricePerUnit, boolean isHotWater) {
        super(date, quantity, pricePerUnit);
        this.isHotWater = isHotWater;
    }

    // ── Méthodes métier ───────────────────────────────────────

    @Override
    public double getEstimatedCost() {
        double base = super.getEstimatedCost();
        return isHotWater ? base * (1 + HOT_WATER_SURCHARGE) : base;
    }

    // ── CSV ───────────────────────────────────────────────────

    /** Format : id,date,quantity,pricePerUnit,isHotWater */
    @Override
    public String toCSV() {
        return id + "," + date + "," + quantity + "," + pricePerUnit + "," + isHotWater;
    }

    public static Water fromCSV(String csv) {
        String[] p = csv.split(",");
        Water w = new Water(
                LocalDate.parse(p[1]),
                Double.parseDouble(p[2]),
                Double.parseDouble(p[3]),
                Boolean.parseBoolean(p[4])
        );
        w.setId(Integer.parseInt(p[0]));
        return w;
    }

    // ── Getters / Setters ─────────────────────────────────────

    public boolean isHotWater() { return isHotWater; }
    public void setHotWater(boolean hotWater) { isHotWater = hotWater; }
}

