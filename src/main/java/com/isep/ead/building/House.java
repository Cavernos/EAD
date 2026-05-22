package com.isep.ead.building;

/**
 * Maison individuelle.
 */
public class House extends Building {

    private int numberOfRooms;
    private boolean hasGarden;

    public House(int id, String name, String address, double surface, int numberOfRooms, boolean hasGarden) {
        super(id, name, address, surface);
        this.numberOfRooms = numberOfRooms;
        this.hasGarden = hasGarden;
    }

    // ── CSV ───────────────────────────────────────────────────

    @Override
    public String toCSV() {
        return "HOUSE;" + id + ";" + name + ";" + address + ";" + surface + ";" + numberOfRooms + ";" + hasGarden;
    }

    public static House fromCSV(String csv) {
        String[] parts = csv.split(";");
        return new House(
                Integer.parseInt(parts[1]),
                parts[2],
                parts[3],
                Double.parseDouble(parts[4]),
                Integer.parseInt(parts[5]),
                Boolean.parseBoolean(parts[6])
        );
    }

    // ── Getters / Setters ─────────────────────────────────────

    public int getNumberOfRooms() { return numberOfRooms; }
    public void setNumberOfRooms(int numberOfRooms) { this.numberOfRooms = numberOfRooms; }

    public boolean isHasGarden() { return hasGarden; }
    public void setHasGarden(boolean hasGarden) { this.hasGarden = hasGarden; }
}

