package com.isep.ead.energy;

import java.time.LocalDate;

/**
 * Consommation d'électricité.
 * Les heures creuses bénéficient d'un tarif réduit de 30 %.
 */
public class Electricity extends Energy {

    /** true = heure creuse (tarif réduit). */
    private boolean isOffPeak;

    private static final double OFF_PEAK_DISCOUNT = 0.30;

    public Electricity(int id, LocalDate date, double quantity, double pricePerUnit, boolean isOffPeak) {
        super(id, date, quantity, pricePerUnit);
        this.isOffPeak = isOffPeak;
    }

    // ── Méthodes métier ───────────────────────────────────────

    /**
     * Coût avec réduction heure creuse si applicable.
     */
    @Override
    public double getEstimatedCost() {
        double baseCost = super.getEstimatedCost();
        return isOffPeak ? baseCost * (1 - OFF_PEAK_DISCOUNT) : baseCost;
    }

    // ── CSV ───────────────────────────────────────────────────

    @Override
    public String toCSV() {
        return "ELECTRICITY;" + id + ";" + date + ";" + quantity + ";" + pricePerUnit + ";" + isOffPeak;
    }

    public static Electricity fromCSV(String csv) {
        String[] parts = csv.split(";");
        return new Electricity(
                Integer.parseInt(parts[1]),
                LocalDate.parse(parts[2]),
                Double.parseDouble(parts[3]),
                Double.parseDouble(parts[4]),
                Boolean.parseBoolean(parts[5])
        );
    }

    // ── Getters / Setters ─────────────────────────────────────

    public boolean isOffPeak() {
        return isOffPeak;
    }

    public void setOffPeak(boolean offPeak) {
        isOffPeak = offPeak;
    }
}

