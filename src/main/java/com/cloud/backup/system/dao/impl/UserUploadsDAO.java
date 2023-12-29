package com.cloud.backup.system.dao.impl;

import com.cloud.backup.system.model.impl.UserUploads;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;


@ApplicationScoped
public class UserUploadsDAO extends GenericDaoImpl<UserUploads> {

    @Inject
    EntityManager entityManager;

    public List<UserUploads> getFilesMarkedAndNotDeleted() {
        return entityManager
                .createQuery("SELECT upload FROM UserUploads upload" +
                " WHERE upload.isUnsafe = true AND upload.removedDate IS NULL", UserUploads.class)
                .getResultList();
    }
}
