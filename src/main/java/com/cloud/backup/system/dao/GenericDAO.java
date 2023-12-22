package com.cloud.backup.system.dao;

import com.cloud.backup.system.model.Model;

public interface GenericDAO<T extends Model> {

    public T findById(Long id, Class<T> clazz);

    public void insert(T t);

    public void delete(T t);

    public T update(T t);
}
