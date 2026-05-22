package com.isep.ead.models;

public interface IModel {
    public static IModel fromCSV(String csv) {
        return null;
    }

    public String toCSV();

}
