package com.isep.ead.models.organization;
public class University extends Organization {
    private int numberOfStudents;
    public University(String name, int numberOfStudents) {
        super(name);
        this.numberOfStudents = numberOfStudents;
    }
    public double getConsumptionPerStudent() {
        return numberOfStudents == 0 ? 0 : getTotalConsumption() / numberOfStudents;
    }
    @Override
    public String toCSV() { return id + "," + getName() + "," + numberOfStudents; }
    public static University fromCSV(String csv) {
        String[] parts = csv.split(",");
        University u = new University(parts[1], Integer.parseInt(parts[2]));
        u.setId(Integer.parseInt(parts[0]));
        return u;
    }
    public int getNumberOfStudents()      { return numberOfStudents; }
    public void setNumberOfStudents(int n){ this.numberOfStudents = n; }
    @Override
    public String toString() {
        return "University{id=" + id + ", name=" + getName() + ", students=" + numberOfStudents + ", buildings=" + getNumberOfBuildings() + "}";
    }
}
