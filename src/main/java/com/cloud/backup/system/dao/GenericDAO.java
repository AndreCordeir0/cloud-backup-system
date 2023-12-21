package com.cloud.backup.system.dao;

import java.io.Serializable;

public interface GenericDAO<T extends Serializable> {

    public T byId(Long id);

    public T insert(T t);

    public T delete(T t);

    public T update(T t);
}
