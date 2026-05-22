package com.isep.ead.models.building;
import com.isep.ead.models.Model;
import com.isep.ead.models.alert.Alert;
import com.isep.ead.models.alert.AlertLevel;
import com.isep.ead.models.energy.Energy;
import java.util.ArrayList;
import java.util.List;
public abstract class Building extends Model implements Cloneable {
    protected int id;
    protected String name;
    protected String address;
    protected double surface;
    protected List<Energy> energyList;
    protected List<Alert> alertList;
    private static final double WARNING_COST_THRESHOLD  = 1_000.0;
    private static final double CRITICAL_COST_THRESHOLD = 5_000.0;
    protected Building(String name, String address, double surface) {
        this.name = name;
        this.address = address;
        this.surface = surface;
        this.energyList = new ArrayList<>();
        this.alertList  = new ArrayList<>();
    }
    public void addEnergy(Energy energy) {
        if (energy != null) {
            energyList.add(energy);
            checkCostAlerts();
        }
    }
    public void removeEnergy(Energy energy) { energyList.remove(energy); }
    public List<Energy> getEnergyTypes() { return new ArrayList<>(energyList); }
    public double getTotalConsumption() {
        return energyList.stream().mapToDouble(Energy::getQuantity).sum();
    }
    public double getEstimatedCost() {
        return energyList.stream().mapToDouble(Energy::getEstimatedCost).sum();
    }
    public List<Alert> getAlerts() { return new ArrayList<>(alertList); }
    public void addAlert(Alert alert) { if (alert != null) alertList.add(alert); }
    private void checkCostAlerts() {
        double cost = getEstimatedCost();
        if (cost >= CRITICAL_COST_THRESHOLD) {
            alertList.add(new Alert("Cout critique : " + String.format("%.2f", cost) + " EUR - " + name, AlertLevel.CRITICAL));
        } else if (cost >= WARNING_COST_THRESHOLD) {
            alertList.add(new Alert("Cout eleve : " + String.format("%.2f", cost) + " EUR - " + name, AlertLevel.WARNING));
        }
    }
    @Override
    public Building clone() {
        try {
            Building cloned   = (Building) super.clone();
            cloned.energyList = new ArrayList<>(this.energyList);
            cloned.alertList  = new ArrayList<>(this.alertList);
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
    public int getId()                  { return id; }
    public void setId(int id)           { this.id = id; }
    public String getName()             { return name; }
    public void setName(String name)    { this.name = name; }
    public String getAddress()          { return address; }
    public void setAddress(String addr) { this.address = addr; }
    public double getSurface()          { return surface; }
    public void setSurface(double s)    { this.surface = s; }
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{id=" + id + ", name=" + name + ", address=" + address + ", surface=" + surface + ", cost=" + String.format("%.2f", getEstimatedCost()) + "}";
    }
}
