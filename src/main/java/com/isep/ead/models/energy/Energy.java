package com.isep.ead.models.energy;
import com.isep.ead.models.Model;
import java.time.LocalDate;
public abstract class Energy extends Model {
    protected int id;
    protected LocalDate date;
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
    public int getId()                        { return id; }
    public void setId(int id)                 { this.id = id; }
    public LocalDate getDate()                { return date; }
    public void setDate(LocalDate date)       { this.date = date; }
    public double getQuantity()               { return quantity; }
    public void setQuantity(double quantity)  { this.quantity = quantity; }
    public double getPricePerUnit()           { return pricePerUnit; }
    public void setPricePerUnit(double price) { this.pricePerUnit = price; }
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{id=" + id + ", date=" + date + ", quantity=" + quantity + ", cost=" + String.format("%.2f", getEstimatedCost()) + "}";
    }
}
