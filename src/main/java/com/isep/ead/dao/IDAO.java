package com.isep.ead.dao;

public interface IDAO {
   Object create(Object object);
    Object getById(int id);
    void remove(Object object);
    Object update(Object object);
}
