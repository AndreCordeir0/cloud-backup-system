package com.cloud.backup.system.dao.impl;

import com.cloud.backup.system.dao.GenericDAO;
import com.cloud.backup.system.model.Model;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

public class GenericDaoImpl<T extends Model> implements GenericDAO<T> {

    @Inject
    EntityManager entityManager;


    @Override
    public T findById(Long id) {
        return null;
    }

    @Override
    public T insert(Model t) {
        return null;
    }

    @Override
    public T delete(Model t) {
        return null;
    }

    @Override
    public T update(Model t) {
        return null;
    }
}
