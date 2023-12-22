package com.cloud.backup.system.dao.impl;

import com.cloud.backup.system.model.impl.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

@ApplicationScoped
public class UserDAO extends GenericDaoImpl<User>{

    @Inject
    EntityManager entityManager;


    public User findByEmail(String email) {
        try {
            return entityManager.createQuery("select user from  User user WHERE user.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        }catch (NoResultException e) {
            return null;
        }
    }
}
