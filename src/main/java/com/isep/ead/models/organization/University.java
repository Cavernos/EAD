package com.isep.ead.models.organization;
public class University extends Organization {
    private int numberOfStudents;
    public University(String name, int numberOfStudents) {
        super(name);
        this.numberOfStudents = numberOfStudents;
    }
    public double getConsumptionPerStudent() {
        if (numberOfStudents == 0) return 0;
        return getTotalConsumption() / numberOfStudents;
    }
    /** Format : id,name,numberOfStudents */
    @Override
    public String toCSV() { return id + "," + getName() + "," + numberOfStudents; }
    public static University fromCSV(String csv) {
        String[] p = csv.split(",");
        University u = new University(p[1], Integer.parseInt(p[2]));
        u.setId(Integer.parseInt(p[0]));
        return u;
    }
    public int getNumberOfStudents() { return numberOfStudents; }
    public void setNumberOfStudents(int n) { this.numberOfStudents = n; }
    @Override
    public String toString() {
        return "University{id=" + id + ", name='" + getName() + "', students=" + numberOfStudents + ", buildings=" + getNumberOfBuildings() + "}";
    }
}
