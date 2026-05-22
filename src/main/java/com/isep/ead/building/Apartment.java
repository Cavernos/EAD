package com.isep.ead.building;

/**
 * Appartement dans une résidence.
 */
public class Apartment extends Building {

    private int floor;
    private String residenceName;

    public Apartment(int id, String name, String address, double surface, int floor, String residenceName) {
        super(id, name, address, surface);
        this.floor = floor;
        this.residenceName = residenceName;
    }

    // ── CSV ───────────────────────────────────────────────────

    @Override
    public String toCSV() {
        return "APARTMENT;" + id + ";" + name + ";" + address + ";" + surface + ";" + floor + ";" + residenceName;
    }

    public static Apartment fromCSV(String csv) {
        String[] parts = csv.split(";");
        return new Apartment(
                Integer.parseInt(parts[1]),
                parts[2],
                parts[3],
                Double.parseDouble(parts[4]),
                Integer.parseInt(parts[5]),
                parts[6]
        );
    }

    // ── Getters / Setters ─────────────────────────────────────

    public int getFloor() { return floor; }
    public void setFloor(int floor) { this.floor = floor; }

    public String getResidenceName() { return residenceName; }
    public void setResidenceName(String residenceName) { this.residenceName = residenceName; }
}

