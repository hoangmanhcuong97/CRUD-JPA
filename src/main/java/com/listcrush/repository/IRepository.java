package com.listcrush.repository;

import java.util.List;

public interface IRepository<T>{
    List<T> showAll();
    void save(T t);
    void delete(int id);
    T findById(int id);
}
