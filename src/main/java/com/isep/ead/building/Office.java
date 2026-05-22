package com.isep.ead.building;

/**
 * Bureau d'entreprise.
 */
public class Office extends Building {

    private int numberOfRooms;
    private int numberOfEmployees;

    public Office(int id, String name, String address, double surface, int numberOfRooms, int numberOfEmployees) {
        super(id, name, address, surface);
        this.numberOfRooms = numberOfRooms;
        this.numberOfEmployees = numberOfEmployees;
    }

    // ── CSV ───────────────────────────────────────────────────

    @Override
    public String toCSV() {
        return "OFFICE;" + id + ";" + name + ";" + address + ";" + surface + ";" + numberOfRooms + ";" + numberOfEmployees;
    }

    public static Office fromCSV(String csv) {
        String[] parts = csv.split(";");
        return new Office(
                Integer.parseInt(parts[1]),
                parts[2],
                parts[3],
                Double.parseDouble(parts[4]),
                Integer.parseInt(parts[5]),
                Integer.parseInt(parts[6])
        );
    }

    // ── Getters / Setters ─────────────────────────────────────

    public int getNumberOfRooms() { return numberOfRooms; }
    public void setNumberOfRooms(int numberOfRooms) { this.numberOfRooms = numberOfRooms; }

    public int getNumberOfEmployees() { return numberOfEmployees; }
    public void setNumberOfEmployees(int numberOfEmployees) { this.numberOfEmployees = numberOfEmployees; }
}

