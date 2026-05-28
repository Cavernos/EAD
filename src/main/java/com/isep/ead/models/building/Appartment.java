package com.isep.ead.models.building;


public class Appartment extends Building {

    private int    floor;
    private String residenceName;


    public Appartment(String name, String address, double surface, int floor, String residenceName) {
        super(name, address, surface);
        this.floor         = floor;
        this.residenceName = residenceName;
    }

    public Appartment() {

    }


    @Override
    public String toCSV() {
        return id + "," + name + "," + address + "," + surface + "," + floor + "," + residenceName;
    }

    public static Appartment fromCSV(String csv) {
        String[] p = csv.split(",");
        Appartment a = new Appartment(p[1], p[2], Double.parseDouble(p[3]),
                Integer.parseInt(p[4]), p[5]);
        a.setId(Integer.parseInt(p[0]));
        return a;
    }

    public int    getFloor()                       { return floor; }
    public void   setFloor(int floor)              { this.floor = floor; }

    public String getResidenceName()               { return residenceName; }
    public void   setResidenceName(String n)       { this.residenceName = n; }
}

