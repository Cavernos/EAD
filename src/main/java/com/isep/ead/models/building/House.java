package com.isep.ead.models.building;


public class House extends Building {

    private int numberOfRooms;
    private boolean hasGarden;


    public House(String name, String address, double surface, int numberOfRooms, boolean hasGarden) {
        super(name, address, surface);
        this.numberOfRooms = numberOfRooms;
        this.hasGarden = hasGarden;
    }

    public House() {

    }


    @Override
    public String toCSV() {
        return id + "," + name + "," + address + "," + surface + "," + numberOfRooms + "," + hasGarden;
    }

    public House fromCSV(String[] fields) {
        House house = new House(fields[1], fields[2], Double.parseDouble(fields[3]),
                Integer.parseInt(fields[4]), Boolean.parseBoolean(fields[5]));
        house.setId(Integer.parseInt(fields[0]));
        return house;
    }



    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int n) {
        this.numberOfRooms = n;
    }

    public boolean hasGarden() {
        return hasGarden;
    }

    public void setHasGarden(boolean garden) {
        this.hasGarden = garden;
    }
}

