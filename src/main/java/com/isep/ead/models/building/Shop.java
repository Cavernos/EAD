       package com.isep.ead.models.building;

/**
 * Commerce / boutique.
 */
public class Shop extends Building {

    private String activitySector;

    /** Constructeur principal — id auto-incrémenté par le DAO. */
    public Shop(String name, String address, double surface, String activitySector) {
        super(name, address, surface);
        this.activitySector = activitySector;
    }

    // ── CSV ───────────────────────────────────────────────────

    /** Format : id,name,address,surface,activitySector */
    @Override
    public String toCSV() {
        return id + "," + name + "," + address + "," + surface + "," + activitySector;
    }

    public static Shop fromCSV(String csv) {
        String[] p = csv.split(",");
        Shop s = new Shop(p[1], p[2], Double.parseDouble(p[3]), p[4]);
        s.setId(Integer.parseInt(p[0]));
        return s;
    }

    // ── Getters / Setters ─────────────────────────────────────

    public String getActivitySector()          { return activitySector; }
    public void   setActivitySector(String s)  { this.activitySector = s; }
}

