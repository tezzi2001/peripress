package org.bondarenko.db.dao;

import java.util.List;
import java.util.Optional;

interface Dao<T> {
    public Optional<T> find(long id);
    public List<T> findAll();
    public boolean save(T... entities);
    public boolean delete(long... ids);
    public boolean delete(T... entities);
}
