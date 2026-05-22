package com.isep.ead.models;

public abstract class Model implements IModel {
    @Override
    public abstract String toCSV();
}
