package com.isep.ead.models.energy;

import com.isep.ead.models.Model;

import java.time.LocalDate;

/**
 * Classe abstraite représentant une consommation énergétique.
 * L'id est géré par le DAO (auto-incrémenté) et ne figure pas dans le constructeur.
 */
public abstract class Energy extends Model {

    protected int id;
    protected LocalDate date;
    protected double quantity;
    protected double pricePerUnit;

    /** Constructeur principal — id auto-incrémenté par le DAO. */
    protected Energy(LocalDate date, double quantity, double pricePerUnit) {
        this.date = date;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
    }

    // ── Méthodes métier ───────────────────────────────────────

    /**
     * Calcule le coût estimé de cette consommation.
     * Peut être surchargé dans les sous-classes pour appliquer des règles tarifaires.
     */
    public double getEstimatedCost() {
        return quantity * pricePerUnit;
    }

    // ── Getters / Setters ─────────────────────────────────────

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) { this.quantity = quantity; }

    public double getPricePerUnit() { return pricePerUnit; }
    public void setPricePerUnit(double pricePerUnit) { this.pricePerUnit = pricePerUnit; }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "{id=" + id + ", date=" + date
                + ", quantity=" + quantity
                + ", cost=" + String.format("%.2f", getEstimatedCost()) + "€}";
    }
}

