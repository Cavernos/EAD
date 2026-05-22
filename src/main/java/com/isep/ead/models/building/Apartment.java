package com.isep.ead.models.building;
public class Apartment extends Building {
    private int floor;
    private String residenceName;
    public Apartment(String name, String address, double surface, int floor, String residenceName) {
        super(name, address, surface);
        this.floor = floor;
        this.residenceName = residenceName;
    }
    @Override
    public String toCSV() {
        return id + "," + name + "," + address + "," + surface + "," + floor + "," + residenceName;
    }
    public static Apartment fromCSV(String csv) {
        String[] parts = csv.split(",");
        Apartment apartment = new Apartment(parts[1], parts[2], Double.parseDouble(parts[3]), Integer.parseInt(parts[4]), parts[5]);
        apartment.setId(Integer.parseInt(parts[0]));
        return apartment;
    }
    public int getFloor()                          { return floor; }
    public void setFloor(int floor)                { this.floor = floor; }
    public String getResidenceName()               { return residenceName; }
    public void setResidenceName(String residenceName) { this.residenceName = residenceName; }
}
