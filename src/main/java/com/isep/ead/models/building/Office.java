package com.isep.ead.models.building;
public class Office extends Building {
    private int numberOfRooms;
    private int numberOfEmployees;
    public Office(String name, String address, double surface, int numberOfRooms, int numberOfEmployees) {
        super(name, address, surface);
        this.numberOfRooms = numberOfRooms;
        this.numberOfEmployees = numberOfEmployees;
    }
    /** Format : id,name,address,surface,numberOfRooms,numberOfEmployees */
    @Override
    public String toCSV() {
        return id + "," + name + "," + address + "," + surface + "," + numberOfRooms + "," + numberOfEmployees;
    }
    public static Office fromCSV(String csv) {
        String[] p = csv.split(",");
        Office o = new Office(p[1], p[2], Double.parseDouble(p[3]), Integer.parseInt(p[4]), Integer.parseInt(p[5]));
        o.setId(Integer.parseInt(p[0]));
        return o;
    }
    public int getNumberOfRooms() { return numberOfRooms; }
    public void setNumberOfRooms(int n) { this.numberOfRooms = n; }
    public int getNumberOfEmployees() { return numberOfEmployees; }
    public void setNumberOfEmployees(int n) { this.numberOfEmployees = n; }
}
