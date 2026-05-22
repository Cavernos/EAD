package com.isep.ead.energy;

import java.time.LocalDate;

/**
 * Consommation de climatisation.
 * Le coût varie selon l'écart entre la température cible et 20 °C (référence).
 */
public class Climatisation extends Energy {

    private double targetTemperature;

    /** Température de référence neutre en °C. */
    private static final double REFERENCE_TEMP = 20.0;

    /** Facteur de surcoût par degré d'écart. */
    private static final double TEMP_FACTOR = 0.05;

    public Climatisation(int id, LocalDate date, double quantity, double pricePerUnit, double targetTemperature) {
        super(id, date, quantity, pricePerUnit);
        this.targetTemperature = targetTemperature;
    }

    // ── Méthodes métier ───────────────────────────────────────

    /**
     * Plus la température cible s'éloigne de la référence (20 °C),
     * plus le coût augmente proportionnellement.
     */
    @Override
    public double getEstimatedCost() {
        double delta = Math.abs(targetTemperature - REFERENCE_TEMP);
        double surcharge = 1.0 + (delta * TEMP_FACTOR);
        return super.getEstimatedCost() * surcharge;
    }

    // ── CSV ───────────────────────────────────────────────────

    @Override
    public String toCSV() {
        return "CLIMATISATION;" + id + ";" + date + ";" + quantity + ";" + pricePerUnit + ";" + targetTemperature;
    }

    public static Climatisation fromCSV(String csv) {
        String[] parts = csv.split(";");
        return new Climatisation(
                Integer.parseInt(parts[1]),
                LocalDate.parse(parts[2]),
                Double.parseDouble(parts[3]),
                Double.parseDouble(parts[4]),
                Double.parseDouble(parts[5])
        );
    }

    // ── Getters / Setters ─────────────────────────────────────

    public double getTargetTemperature() {
        return targetTemperature;
    }

    public void setTargetTemperature(double targetTemperature) {
        this.targetTemperature = targetTemperature;
    }
}

