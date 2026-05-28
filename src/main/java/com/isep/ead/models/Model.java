package com.isep.ead.models;

public abstract class Model<T extends Model<T>> implements IModel<T>{
    @Override
    public abstract String toCSV();

    @Override
    public abstract T fromCSV(String[] fields);

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void setId(int id) {
    }
}
