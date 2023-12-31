package com.cloud.backup.system.dao.impl;

import com.cloud.backup.system.model.impl.UserUploads;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;
import java.util.UUID;


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

    public UserUploads getFileFromUUID(UUID uuid, Long userId) {
        try {
            return entityManager.createQuery("SELECT upload FROM UserUploads upload " +
                            " WHERE upload.uuid = :uuid AND upload.user.id = :userId AND upload.isUnsafe = false", UserUploads.class)
                    .setParameter("uuid", uuid)
                    .setParameter("userId", userId)
                    .getSingleResult();
        }catch (NoResultException e) {
            return null;
        }
    }
}
