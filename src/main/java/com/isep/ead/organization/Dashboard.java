package com.isep.ead.organization;

import com.isep.ead.alert.Alert;

import java.util.ArrayList;
import java.util.List;

/**
 * Dashboard principal de l'application.
 * Agrège toutes les organisations et expose les alertes globales.
 */
public class Dashboard {

    private List<Organization> organizations;

    public Dashboard() {
        this.organizations = new ArrayList<>();
    }

    // ── Gestion des organisations ─────────────────────────────

    public void addOrganization(Organization org) {
        if (org != null && !organizations.contains(org)) {
            organizations.add(org);
        }
    }

    public void removeOrganization(Organization org) {
        organizations.remove(org);
    }

    public List<Organization> getOrganizations() {
        return new ArrayList<>(organizations);
    }

    // ── Calculs globaux ───────────────────────────────────────

    /** Coût total estimé pour toutes les organisations. */
    public double getTotalEstimatedCost() {
        return organizations.stream()
                .mapToDouble(Organization::getEstimatedCost)
                .sum();
    }

    /** Consommation totale de toutes les organisations. */
    public double getTotalConsumption() {
        return organizations.stream()
                .mapToDouble(Organization::getTotalConsumption)
                .sum();
    }

    /**
     * Collecte toutes les alertes de tous les bâtiments de toutes les organisations.
     *
     * @return liste globale des alertes
     */
    public List<Alert> getAlerts() {
        List<Alert> allAlerts = new ArrayList<>();
        organizations.forEach(org ->
                org.getBuildings().forEach(building ->
                        allAlerts.addAll(building.getAlerts())
                )
        );
        return allAlerts;
    }

    /** Retourne uniquement les alertes non lues. */
    public List<Alert> getUnreadAlerts() {
        List<Alert> unread = new ArrayList<>();
        getAlerts().stream()
                .filter(a -> !a.isRead())
                .forEach(unread::add);
        return unread;
    }

    @Override
    public String toString() {
        return "Dashboard{organizations=" + organizations.size()
                + ", totalCost=" + String.format("%.2f", getTotalEstimatedCost()) + "€"
                + ", unreadAlerts=" + getUnreadAlerts().size() + "}";
    }
}

