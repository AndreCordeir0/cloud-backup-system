package com.cloud.backup.system.dao.impl;

import com.cloud.backup.system.dao.GenericDAO;
import com.cloud.backup.system.model.Model;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class GenericDaoImpl<T extends Model> implements GenericDAO<T> {
    @PersistenceContext
    EntityManager em;


    @Override
    public T findById(Long id, Class<T> clazz) {
        return em.find(clazz,id);
    }

    @Override
    public void insert(Model t) {
        em.persist(t);
    }

    @Override
    public void delete(Model t) {
        em.remove(t);
    }

    @Override
    public T update(Model t) {
        return (T) em.merge(t);
    }
}
