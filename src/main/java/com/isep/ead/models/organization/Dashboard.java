package com.isep.ead.models.organization;
import com.isep.ead.models.alert.Alert;
import java.util.ArrayList;
import java.util.List;
public class Dashboard {
    private List<Organization> organizations;
    public Dashboard() { this.organizations = new ArrayList<>(); }
    public void addOrganization(Organization org) { if (org != null && !organizations.contains(org)) organizations.add(org); }
    public void removeOrganization(Organization org) { organizations.remove(org); }
    public List<Organization> getOrganizations() { return new ArrayList<>(organizations); }
    public double getTotalEstimatedCost() { return organizations.stream().mapToDouble(Organization::getEstimatedCost).sum(); }
    public double getTotalConsumption()   { return organizations.stream().mapToDouble(Organization::getTotalConsumption).sum(); }
    public List<Alert> getAlerts() {
        List<Alert> all = new ArrayList<>();
        organizations.forEach(org -> org.getBuildings().forEach(b -> all.addAll(b.getAlerts())));
        return all;
    }
    public List<Alert> getUnreadAlerts() {
        List<Alert> unread = new ArrayList<>();
        getAlerts().stream().filter(a -> !a.isRead()).forEach(unread::add);
        return unread;
    }
    @Override
    public String toString() {
        return "Dashboard{organizations=" + organizations.size() + ", totalCost=" + String.format("%.2f", getTotalEstimatedCost()) + "EUR, unreadAlerts=" + getUnreadAlerts().size() + "}";
    }
}
