package com.isep.ead.models.building;

public class House extends Building {
    private int numberOfRooms;
    private boolean hasGarden;

    public House(String name, String address, double surface, int numberOfRooms, boolean hasGarden) {
        super(name, address, surface);
        this.numberOfRooms = numberOfRooms;
        this.hasGarden = hasGarden;
    }

    @Override
    public String toCSV() {
        return id + "," + name + "," + address + "," + surface + "," + numberOfRooms + "," + hasGarden;
    }

    public static House fromCSV(String csv) {
        String[] parts = csv.split(",");
        House house = new House(parts[1], parts[2], Double.parseDouble(parts[3]), Integer.parseInt(parts[4]), Boolean.parseBoolean(parts[5]));
        house.setId(Integer.parseInt(parts[0]));
        return house;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public boolean isHasGarden() {
        return hasGarden;
    }

    public void setHasGarden(boolean hasGarden) {
        this.hasGarden = hasGarden;
    }
}
