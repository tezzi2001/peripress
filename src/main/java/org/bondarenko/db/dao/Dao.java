package org.bondarenko.db.dao;

import java.util.List;
import java.util.Optional;

interface Dao<T> {
    public Optional<T> find(long id);
    public List<T> findAll();
    public boolean save(T entity);
    public boolean delete(long id);
    public boolean delete(T entity);
}
