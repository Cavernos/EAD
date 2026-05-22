package com.isep.ead.energy;

import com.isep.ead.model.Model;

import java.time.LocalDate;

/**
 * Classe abstraite représentant une consommation énergétique.
 * Toutes les formes d'énergie héritent de cette classe.
 */
public abstract class Energy extends Model {

    protected int id;
    protected LocalDate date;
    protected double quantity;
    protected double pricePerUnit;

    protected Energy(int id, LocalDate date, double quantity, double pricePerUnit) {
        this.id = id;
        this.date = date;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
    }

    // ── Méthodes métier ───────────────────────────────────────

    /**
     * Calcule le coût estimé de cette consommation.
     *
     * @return coût = quantité × prix unitaire
     */
    public double getEstimatedCost() {
        return quantity * pricePerUnit;
    }

    // ── Getters / Setters ─────────────────────────────────────

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{id=" + id + ", date=" + date
                + ", quantity=" + quantity + ", cost=" + getEstimatedCost() + "}";
    }
}

