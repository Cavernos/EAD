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

    /**
     * Format : id,name,numberOfStudents
     */
    @Override
    public String toCSV() {
        return id + "," + getName() + "," + numberOfStudents;
    }
    @Override
    public University fromCSV(String[] fields) {
        University university = new University(fields[1], Integer.parseInt(fields[2]));
        university.setId(Integer.parseInt(fields[0]));
        return university;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int n) {
        this.numberOfStudents = n;
    }

    @Override
    public String toString() {
        return "University{id=" + id + ", name='" + getName() + "', students=" + numberOfStudents + ", buildings=" + this.countBuildings() + "}";
    }
}
