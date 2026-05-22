package com.isep.ead.models.energy;
import com.isep.ead.models.Model;
import java.time.LocalDate;
public class Energy extends Model {
    protected int id;
    protected LocalDate date;
    protected double quantity;
    protected double pricePerUnit;
    public Energy(LocalDate date, double quantity, double pricePerUnit) {
        this.date = date;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
    }
    public double getEstimatedCost()              { return quantity * pricePerUnit; }
    public LocalDate getDate()                    { return date; }
    public void setDate(LocalDate date)           { this.date = date; }
    public double getQuantity()                   { return quantity; }
    public void setQuantity(double quantity)      { this.quantity = quantity; }
    public double getPricePerUnit()               { return pricePerUnit; }
    public void setPricePerUnit(double pricePerUnit) { this.pricePerUnit = pricePerUnit; }
    public int getId()                            { return id; }
    public void setId(int id)                     { this.id = id; }
    @Override
    public String toCSV() { return id + "," + date + "," + quantity + "," + pricePerUnit; }
    public static Energy fromCSV(String csv) {
        String[] parts = csv.split(",");
        Energy energy = new Energy(LocalDate.parse(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3]));
        energy.setId(Integer.parseInt(parts[0]));
        return energy;
    }
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{id=" + id + ", date=" + date + ", quantity=" + quantity + ", cost=" + String.format("%.2f", getEstimatedCost()) + "}";
    }
}
