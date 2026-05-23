package com.isep.ead.dao;

public interface IDAO<T> {
   T create(T object);
    T getById(int id);
    void remove(T object);
    T update(T object);
}
