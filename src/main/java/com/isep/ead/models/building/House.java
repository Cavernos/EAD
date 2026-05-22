package com.isep.ead.models.building;

/**
 * Maison individuelle.
 */
public class House extends Building {

    private int     numberOfRooms;
    private boolean hasGarden;

    /** Constructeur principal — id auto-incrémenté par le DAO. */
    public House(String name, String address, double surface, int numberOfRooms, boolean hasGarden) {
        super(name, address, surface);
        this.numberOfRooms = numberOfRooms;
        this.hasGarden     = hasGarden;
    }

    // ── CSV ───────────────────────────────────────────────────

    /** Format : id,name,address,surface,numberOfRooms,hasGarden */
    @Override
    public String toCSV() {
        return id + "," + name + "," + address + "," + surface + "," + numberOfRooms + "," + hasGarden;
    }

    public static House fromCSV(String csv) {
        String[] p = csv.split(",");
        House h = new House(p[1], p[2], Double.parseDouble(p[3]),
                Integer.parseInt(p[4]), Boolean.parseBoolean(p[5]));
        h.setId(Integer.parseInt(p[0]));
        return h;
    }

    // ── Getters / Setters ─────────────────────────────────────

    public int  getNumberOfRooms()              { return numberOfRooms; }
    public void setNumberOfRooms(int n)         { this.numberOfRooms = n; }

    public boolean isHasGarden()               { return hasGarden; }
    public void    setHasGarden(boolean g)     { this.hasGarden = g; }
}

