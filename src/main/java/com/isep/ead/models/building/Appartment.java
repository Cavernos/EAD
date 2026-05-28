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
        return id + ",Appartment," + name + "," + address + "," + surface + "," + organizationId + "," + floor + "," + residenceName;
    }

    @Override
    public Appartment fromCSV(String[] fields) {
        // format: id,Appartment,name,address,surface,organizationId[,floor,residenceName]
        Appartment a = new Appartment();
        a.setId(Integer.parseInt(fields[0]));
        a.setName(fields[2]);
        a.setAddress(fields[3]);
        try { a.setSurface(Double.parseDouble(fields[4])); } catch (Exception ignored) {}
        if (fields.length > 5) try { a.setOrganizationId(Integer.parseInt(fields[5])); } catch (Exception ignored) {}
        if (fields.length > 6) try { a.setFloor(Integer.parseInt(fields[6])); } catch (Exception ignored) {}
        if (fields.length > 7) a.setResidenceName(fields[7]);
        return a;
    }

    public int    getFloor()                       { return floor; }
    public void   setFloor(int floor)              { this.floor = floor; }

    public String getResidenceName()               { return residenceName; }
    public void   setResidenceName(String n)       { this.residenceName = n; }
}

