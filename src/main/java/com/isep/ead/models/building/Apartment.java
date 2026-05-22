package com.isep.ead.models.building;

/**
 * Appartement dans une résidence.
 */
public class Apartment extends Building {

    private int    floor;
    private String residenceName;

    /** Constructeur principal — id auto-incrémenté par le DAO. */
    public Apartment(String name, String address, double surface, int floor, String residenceName) {
        super(name, address, surface);
        this.floor         = floor;
        this.residenceName = residenceName;
    }

    // ── CSV ───────────────────────────────────────────────────

    /** Format : id,name,address,surface,floor,residenceName */
    @Override
    public String toCSV() {
        return id + "," + name + "," + address + "," + surface + "," + floor + "," + residenceName;
    }

    public static Apartment fromCSV(String csv) {
        String[] p = csv.split(",");
        Apartment a = new Apartment(p[1], p[2], Double.parseDouble(p[3]),
                Integer.parseInt(p[4]), p[5]);
        a.setId(Integer.parseInt(p[0]));
        return a;
    }

    // ── Getters / Setters ─────────────────────────────────────

    public int    getFloor()                       { return floor; }
    public void   setFloor(int floor)              { this.floor = floor; }

    public String getResidenceName()               { return residenceName; }
    public void   setResidenceName(String n)       { this.residenceName = n; }
}

