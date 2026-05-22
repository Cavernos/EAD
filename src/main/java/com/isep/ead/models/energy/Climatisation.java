package com.isep.ead.models.energy;

import java.time.LocalDate;

/**
 * Consommation de climatisation.
 * Surcoût proportionnel à l'écart par rapport à la température de référence (20 °C).
 */
public class Climatisation extends Energy {

    private double targetTemperature;

    private static final double REFERENCE_TEMP = 20.0;
    private static final double TEMP_FACTOR     = 0.05;

    /** Constructeur principal — id auto-incrémenté par le DAO. */
    public Climatisation(LocalDate date, double quantity, double pricePerUnit, double targetTemperature) {
        super(date, quantity, pricePerUnit);
        this.targetTemperature = targetTemperature;
    }

    // ── Méthodes métier ───────────────────────────────────────

    @Override
    public double getEstimatedCost() {
        double delta     = Math.abs(targetTemperature - REFERENCE_TEMP);
        double surcharge = 1.0 + (delta * TEMP_FACTOR);
        return super.getEstimatedCost() * surcharge;
    }

    // ── CSV ───────────────────────────────────────────────────

    /** Format : id,date,quantity,pricePerUnit,targetTemperature */
    @Override
    public String toCSV() {
        return id + "," + date + "," + quantity + "," + pricePerUnit + "," + targetTemperature;
    }

    public static Climatisation fromCSV(String csv) {
        String[] p = csv.split(",");
        Climatisation c = new Climatisation(
                LocalDate.parse(p[1]),
                Double.parseDouble(p[2]),
                Double.parseDouble(p[3]),
                Double.parseDouble(p[4])
        );
        c.setId(Integer.parseInt(p[0]));
        return c;
    }

    // ── Getters / Setters ─────────────────────────────────────

    public double getTargetTemperature() { return targetTemperature; }
    public void setTargetTemperature(double t) { this.targetTemperature = t; }
}

