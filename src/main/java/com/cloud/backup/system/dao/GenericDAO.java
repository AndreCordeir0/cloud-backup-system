package com.cloud.backup.system.dao;

import com.cloud.backup.system.model.Model;

public interface GenericDAO<T extends Model> {

    public T findById(Long id);

    public T insert(T t);

    public T delete(T t);

    public T update(T t);
}
