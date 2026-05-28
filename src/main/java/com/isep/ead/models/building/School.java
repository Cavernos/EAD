package com.isep.ead.models.building;

public class School extends Building {

    private int numberOfStudents;

    public School() {}

    public School(String name, String address, double surface, int numberOfStudents) {
        super(name, address, surface);
        this.numberOfStudents = numberOfStudents;
    }

    @Override
    public String toCSV() {
        return id + ",School," + name + "," + address + "," + surface + "," + organizationId + "," + numberOfStudents;
    }

    @Override
    public School fromCSV(String[] fields) {
        // format: id,School,name,address,surface,organizationId[,numberOfStudents]
        School school = new School();
        school.setId(Integer.parseInt(fields[0]));
        school.setName(fields[2]);
        school.setAddress(fields[3]);
        try { school.setSurface(Double.parseDouble(fields[4])); } catch (Exception ignored) {}
        if (fields.length > 5) try { school.setOrganizationId(Integer.parseInt(fields[5])); } catch (Exception ignored) {}
        if (fields.length > 6) try { school.setNumberOfStudents(Integer.parseInt(fields[6])); } catch (Exception ignored) {}
        return school;
    }

    public int getNumberOfStudents() { return numberOfStudents; }
    public void setNumberOfStudents(int n) { this.numberOfStudents = n; }
}
