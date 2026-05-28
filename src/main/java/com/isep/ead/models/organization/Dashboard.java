package com.isep.ead.models.organization;

import com.isep.ead.models.alert.Alert;

import java.util.ArrayList;
import java.util.List;
public class Dashboard {
    private final List<Organization> organizations = new ArrayList<>();

    public Dashboard() {
    }

    public void addOrganization(Organization organization) {
        if (organization != null && !organizations.contains(organization)){
            organizations.add(organization);
        }
    }

    public void removeOrganization(Organization organization) {
        organizations.remove(organization);
    }

    public List<Organization> getOrganizations() {
        return new ArrayList<>(organizations);
    }

    public double getTotalEstimatedCost() {
        double cost = 0.0;
        for (Organization organization: this.organizations) {
            cost += organization.getEstimatedCost();
        }
        return cost;
    }

    public double getTotalConsumption() {
        double consumption = 0.0;
        for (Organization organization: this.organizations) {
            consumption += organization.getTotalConsumption();
        }
        return consumption;
    }

    public List<Alert> getAlerts() {
        List<Alert> all = new ArrayList<>();
        organizations.forEach(organization -> organization.getBuildings().forEach(building -> all.addAll(building.getAlerts())));
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
