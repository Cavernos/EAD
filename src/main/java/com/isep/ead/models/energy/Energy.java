package com.isep.ead.models.energy;

import com.isep.ead.models.IModel;
import com.isep.ead.models.Model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Classe abstraite représentant une consommation énergétique.
 * Chaque sous-classe implémente IModel<SousClasse> pour être compatible avec DAO.
 */
public abstract class Energy {

    protected int id;
    protected int buildingId;
    protected LocalDate date;
    protected LocalTime time = LocalTime.of(0, 0);
    protected double quantity;
    protected double pricePerUnit;


    protected Energy(LocalDate date, double quantity, double pricePerUnit) {
        this.date = date;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
    }


    public double getEstimatedCost() {
        return quantity * pricePerUnit;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getBuildingId() { return buildingId; }
    public void setBuildingId(int buildingId) { this.buildingId = buildingId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getTime() { return time; }
    public void setTime(LocalTime time) { this.time = time; }

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
