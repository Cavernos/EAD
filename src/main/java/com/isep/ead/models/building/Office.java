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
    public static Office fromCSV(String csv) {
        String[] parts = csv.split(",");
        Office office = new Office(parts[1], parts[2], Double.parseDouble(parts[3]), Integer.parseInt(parts[4]), Integer.parseInt(parts[5]));
        office.setId(Integer.parseInt(parts[0]));
        return office;
    }
    public int getNumberOfRooms()                        { return numberOfRooms; }
    public void setNumberOfRooms(int numberOfRooms)      { this.numberOfRooms = numberOfRooms; }
    public int getNumberOfEmployees()                    { return numberOfEmployees; }
    public void setNumberOfEmployees(int numberOfEmployees) { this.numberOfEmployees = numberOfEmployees; }
}
