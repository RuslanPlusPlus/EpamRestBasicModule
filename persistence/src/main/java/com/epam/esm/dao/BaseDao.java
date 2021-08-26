package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

public interface BaseDao <T>{
    public List<T> findAll();
    Optional<T> findById(long id);
    void delete(long id);
    Optional<T> save(T obj);
}
