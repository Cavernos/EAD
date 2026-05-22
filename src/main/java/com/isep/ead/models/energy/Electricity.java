package com.isep.ead.models.energy;

import java.time.LocalDate;

/**
 * Consommation d'électricité.
 * Les heures creuses bénéficient d'un tarif réduit de 30 %.
 */
public class Electricity extends Energy {

    private boolean isOffPeak;

    private static final double OFF_PEAK_DISCOUNT = 0.30;

    /** Constructeur principal — id auto-incrémenté par le DAO. */
    public Electricity(LocalDate date, double quantity, double pricePerUnit, boolean isOffPeak) {
        super(date, quantity, pricePerUnit);
        this.isOffPeak = isOffPeak;
    }

    // ── Méthodes métier ───────────────────────────────────────

    @Override
    public double getEstimatedCost() {
        double base = super.getEstimatedCost();
        return isOffPeak ? base * (1 - OFF_PEAK_DISCOUNT) : base;
    }

    // ── CSV ───────────────────────────────────────────────────

    /** Format : id,date,quantity,pricePerUnit,isOffPeak */
    @Override
    public String toCSV() {
        return id + "," + date + "," + quantity + "," + pricePerUnit + "," + isOffPeak;
    }

    public static Electricity fromCSV(String csv) {
        String[] p = csv.split(",");
        Electricity e = new Electricity(
                LocalDate.parse(p[1]),
                Double.parseDouble(p[2]),
                Double.parseDouble(p[3]),
                Boolean.parseBoolean(p[4])
        );
        e.setId(Integer.parseInt(p[0]));
        return e;
    }

    // ── Getters / Setters ─────────────────────────────────────

    public boolean isOffPeak() { return isOffPeak; }
    public void setOffPeak(boolean offPeak) { isOffPeak = offPeak; }
}

