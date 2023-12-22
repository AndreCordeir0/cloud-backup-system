package com.cloud.backup.system.dao.impl;

import com.cloud.backup.system.model.impl.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class UserDAO extends GenericDaoImpl<User>{

    @Inject
    EntityManager entityManager;

}
