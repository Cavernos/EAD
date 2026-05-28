package com.isep.ead.models.building;

import com.isep.ead.models.IModel;

public class Office extends Building {
    private int numberOfRooms;
    private int numberOfEmployees;

    public Office(String name, String address, double surface, int numberOfRooms, int numberOfEmployees) {
        super(name, address, surface);
        this.numberOfRooms = numberOfRooms;
        this.numberOfEmployees = numberOfEmployees;
    }

    public Office() {

    }


    @Override
    public String toCSV() {
        return id + ",Office," + name + "," + address + "," + surface + "," + organizationId + "," + numberOfRooms + "," + numberOfEmployees;
    }

    @Override
    public Office fromCSV(String[] fields) {
        // format: id,Office,name,address,surface,organizationId[,numberOfRooms,numberOfEmployees]
        Office office = new Office();
        office.setId(Integer.parseInt(fields[0]));
        office.setName(fields[2]);
        office.setAddress(fields[3]);
        try { office.setSurface(Double.parseDouble(fields[4])); } catch (Exception ignored) {}
        if (fields.length > 5) try { office.setOrganizationId(Integer.parseInt(fields[5])); } catch (Exception ignored) {}
        if (fields.length > 6) try { office.setNumberOfRooms(Integer.parseInt(fields[6])); } catch (Exception ignored) {}
        if (fields.length > 7) try { office.setNumberOfEmployees(Integer.parseInt(fields[7])); } catch (Exception ignored) {}
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

    @Override
    public String getFrenchType() {
        return "Bureau";
    }
}
