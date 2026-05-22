package com.isep.ead.building;

/**
 * Commerce / boutique.
 */
public class Shop extends Building {

    private String activitySector;

    public Shop(int id, String name, String address, double surface, String activitySector) {
        super(id, name, address, surface);
        this.activitySector = activitySector;
    }

    // ── CSV ───────────────────────────────────────────────────

    @Override
    public String toCSV() {
        return "SHOP;" + id + ";" + name + ";" + address + ";" + surface + ";" + activitySector;
    }

    public static Shop fromCSV(String csv) {
        String[] parts = csv.split(";");
        return new Shop(
                Integer.parseInt(parts[1]),
                parts[2],
                parts[3],
                Double.parseDouble(parts[4]),
                parts[5]
        );
    }

    // ── Getters / Setters ─────────────────────────────────────

    public String getActivitySector() { return activitySector; }
    public void setActivitySector(String activitySector) { this.activitySector = activitySector; }
}

