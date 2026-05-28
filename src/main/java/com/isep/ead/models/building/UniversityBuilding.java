package com.isep.ead.models.building;

public class UniversityBuilding extends Building {

    private int numberOfStudents;

    public UniversityBuilding() {}

    public UniversityBuilding(String name, String address, double surface, int numberOfStudents) {
        super(name, address, surface);
        this.numberOfStudents = numberOfStudents;
    }

    @Override
    public String toCSV() {
        return id + ",UniversityBuilding," + name + "," + address + "," + surface + "," + organizationId + "," + numberOfStudents;
    }

    @Override
    public UniversityBuilding fromCSV(String[] fields) {
        // format: id,UniversityBuilding,name,address,surface,organizationId[,numberOfStudents]
        UniversityBuilding ub = new UniversityBuilding();
        ub.setId(Integer.parseInt(fields[0]));
        ub.setName(fields[2]);
        ub.setAddress(fields[3]);
        try { ub.setSurface(Double.parseDouble(fields[4])); } catch (Exception ignored) {}
        if (fields.length > 5) try { ub.setOrganizationId(Integer.parseInt(fields[5])); } catch (Exception ignored) {}
        if (fields.length > 6) try { ub.setNumberOfStudents(Integer.parseInt(fields[6])); } catch (Exception ignored) {}
        return ub;
    }

    public int getNumberOfStudents() { return numberOfStudents; }
    public void setNumberOfStudents(int n) { this.numberOfStudents = n; }
}
