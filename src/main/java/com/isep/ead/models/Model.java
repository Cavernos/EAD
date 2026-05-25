package com.isep.ead.models;

public class Model implements IModel {
    @Override
    public String toCSV() {
        return "";
    }
    public static Model fromCSV(String csv) {
        return new Model();
    }
}
