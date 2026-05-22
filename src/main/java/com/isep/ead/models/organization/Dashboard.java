package com.isep.ead.models.organization;
import com.isep.ead.models.alert.Alert;
import java.util.ArrayList;
import java.util.List;
public class Dashboard {
    private List<Organization> organizationList;
    public Dashboard() { this.organizationList = new ArrayList<>(); }
    public void addOrganization(Organization org) {
        if (org != null && !organizationList.contains(org)) organizationList.add(org);
    }
    public void removeOrganization(Organization org) { organizationList.remove(org); }
    public List<Organization> getOrganizations() { return new ArrayList<>(organizationList); }
    public double getTotalEstimatedCost() { return organizationList.stream().mapToDouble(Organization::getEstimatedCost).sum(); }
    public double getTotalConsumption()   { return organizationList.stream().mapToDouble(Organization::getTotalConsumption).sum(); }
    public List<Alert> getAlerts() {
        List<Alert> alerts = new ArrayList<>();
        organizationList.forEach(org -> org.getBuildings().forEach(b -> alerts.addAll(b.getAlerts())));
        return alerts;
    }
    public List<Alert> getUnreadAlerts() {
        List<Alert> unread = new ArrayList<>();
        getAlerts().stream().filter(a -> !a.isRead()).forEach(unread::add);
        return unread;
    }
    @Override
    public String toString() {
        return "Dashboard{organizations=" + organizationList.size() + ", cost=" + String.format("%.2f", getTotalEstimatedCost()) + ", unreadAlerts=" + getUnreadAlerts().size() + "}";
    }
}
