package org.bondarenko.db.dao;

import java.util.List;
import java.util.Optional;

interface Dao<T> {
    Optional<T> find(long id);
    List<T> findAll();
    boolean save(T entity);
    boolean delete(long id);
    boolean delete(T entity);
}
