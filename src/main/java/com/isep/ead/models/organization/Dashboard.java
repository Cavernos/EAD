package com.isep.ead.models.organization;
import com.isep.ead.models.alert.Alert;
import java.util.ArrayList;
import java.util.List;
public class Dashboard {
    private List<Organization> organizations;
    public Dashboard() { this.organizations = new ArrayList<>(); }
    public void addOrganization(Organization org) {
        if (org != null && !organizations.contains(org)) organizations.add(org);
    }
    public void removeOrganization(Organization org) { organizations.remove(org); }
    public List<Organization> getOrganizations() { return new ArrayList<>(organizations); }
    public List<Alert> getAlerts() {
        List<Alert> alerts = new ArrayList<>();
        organizations.forEach(org -> org.getBuildings().forEach(b -> alerts.addAll(b.getAlerts())));
        return alerts;
    }
    @Override
    public String toString() {
        return "Dashboard{organizations=" + organizations.size() + "}";
    }
}
