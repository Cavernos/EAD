package com.isep.ead.dao;

import com.isep.ead.models.IModel;
import com.isep.ead.utils.CSVHandler;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DAO<T extends IModel<T>> implements IDAO<T>{
    public static int idSequence = 1;
    private final CSVHandler csvHandler;
    private final Class<T> model;

    public DAO(Class<T> model) {
        this(model, model.getSimpleName().toLowerCase() + ".csv");
    }
    public DAO(Class<T> model, String filename) {
        this.csvHandler = new CSVHandler(filename);
        idSequence = this.getLastId();
        this.model = model;
    }

    @Override
    public T create(T model) {
        model.setId(idSequence++);
        this.csvHandler.write(model.toCSV());
        return model;
    }

    @Override
    public T getById(int id) {
        List<T> models = this.getAll();
        if (id - 1 < models.size()) {
            return models.get(id - 1);
        }
        return null;
    }

    @Override
    public List<T> getAll() {
        String[] rows = this.csvHandler.read();
        List<T> records = new ArrayList<>();
        for (String row : rows) {
            if (row.contains(",")){
                T model = Objects.requireNonNull(this.createNewModelInstance()).fromCSV(row.split(","));
                records.add(model);
            }
        }
        return records;
    }

    private int getLastId() {
        String row = this.csvHandler.getLastRow();
        if (!row.contains(",")) {
            return 1;
        }
        return Integer.parseInt(row.split(",")[0]) + 1;
    }

    @Override
    public void remove(T model) {
        if (model != null) {
            this.csvHandler.remove(model.getId());
        }
    }
    @Override
    public T update(T model) {
        return Objects.requireNonNull(this.createNewModelInstance()).fromCSV(this.csvHandler.update(model.getId(), model.toCSV()).split(","));
    }

    private T createNewModelInstance() {
        try {
            return this.model.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            System.out.println(this.model.getName() + " Model instanciation problem");
            return null;
        }
    }
}
