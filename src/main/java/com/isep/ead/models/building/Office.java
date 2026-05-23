package com.isep.ead.models.building;

public class Office extends Building {
    private int numberOfRooms;
    private int numberOfEmployees;

    public Office(String name, String address, double surface, int numberOfRooms, int numberOfEmployees) {
        super(name, address, surface);
        this.numberOfRooms = numberOfRooms;
        this.numberOfEmployees = numberOfEmployees;
    }


    @Override
    public String toCSV() {
        return id + "," + name + "," + address + "," + surface + "," + numberOfRooms + "," + numberOfEmployees;
    }

    public Office fromCSV(String[] fields) {

        Office office = new Office(fields[1], fields[2], Double.parseDouble(fields[3]), Integer.parseInt(fields[4]), Integer.parseInt(fields[5]));
        office.setId(Integer.parseInt(fields[0]));
        return office;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(int n) {
        this.numberOfEmployees = n;
    }
}
