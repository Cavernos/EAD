package com.isep.ead.models.organization;
import com.isep.ead.models.alert.Alert;
import java.util.ArrayList;
import java.util.List;
public class Dashboard {
    private List<Organization> organizations;
    public Dashboard() { this.organizations = new ArrayList<>(); }
    public void addOrganization(Organization organization) {
        if (organization != null && !organizations.contains(organization)) organizations.add(organization);
    }
    public void removeOrganization(Organization organization) { organizations.remove(organization); }
    public List<Organization> getOrganizations() { return new ArrayList<>(organizations); }
    public List<Alert> getAlerts() {
        List<Alert> alerts = new ArrayList<>();
        organizations.forEach(organization -> organization.getBuildings().forEach(building -> alerts.addAll(building.getAlerts())));
        return alerts;
    }
    @Override
    public String toString() {
        return "Dashboard{organizations=" + organizations.size() + "}";
    }
}
