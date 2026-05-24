package com.isep.ead.dao;

import java.util.List;

public interface IDAO<T> {
   T create(T object);
    T getById(int id);
    List<T> getAll();
    void remove(T object);
    T update(T object);
}
