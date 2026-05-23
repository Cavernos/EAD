package com.isep.ead.dao;

import com.isep.ead.models.IModel;
import com.isep.ead.utils.CSVHandler;

public class DAO<T extends IModel> implements IDAO<T>{
    public static int idSequence = 1;
    private final CSVHandler csvHandler;
    private final Class<T> model;

    public DAO(Class<T> model) {
        this.csvHandler = new CSVHandler(".csv");
        idSequence = this.getLastId();
        this.model = model;

    }


    public T create(T model) {
        model.setId(idSequence++);
        this.csvHandler.write(model.toCSV());
        return model;
    }

    @Override
    public T getById(int id) {
        String[] rows = this.csvHandler.read();
        for (String row : rows) {
            T model = T.fromCSV(row.split(","));
            if (model.getId() == id) {
                return model;
            }
        }
        return null;
    }
    private int getLastId() {
        String row = this.csvHandler.getLastRow();
        if (!row.contains(",")) {
            return 1;
        }
        return Integer.parseInt(row.split(",")[0]);
    }

    @Override
    public void remove(IModel model) {
        if (model != null) {
            this.csvHandler.remove(model.getId());
        }
    }
    @Override
    public T update(T model) {
        return T.fromCSV(this.csvHandler.update(model.getId(), model.toCSV()).split(","));
    }
}
