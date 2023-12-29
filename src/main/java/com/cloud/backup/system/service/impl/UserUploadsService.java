package com.cloud.backup.system.service.impl;

import com.cloud.backup.system.config.impl.VolumeImpl;
import com.cloud.backup.system.dao.impl.UserDAO;
import com.cloud.backup.system.dao.impl.UserUploadsDAO;
import com.cloud.backup.system.exception.impl.CloudBusinessException;
import com.cloud.backup.system.model.impl.FormData;
import com.cloud.backup.system.model.impl.User;
import com.cloud.backup.system.model.impl.UserUploads;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static jakarta.ws.rs.core.Response.Status;

@ApplicationScoped
public class UserUploadsService {
    Logger logger = LoggerFactory.getLogger(UserUploadsService.class);

    @Inject
    VolumeImpl volumeUtil;

    @Inject
    UserDAO userDAO;

    @Inject
    UserUploadsDAO userUploadsDAO;

    public String createFolder(String folderName, String userId) {
        if (folderName == null) {
            throw new CloudBusinessException("The folder name cannot be null.", Status.BAD_REQUEST);
        }
        volumeUtil.createFolder(folderName, userId);
        return "Folder created";
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public String saveFile(@MultipartForm FormData formData, String userId) {
        User user = userDAO.findById(Long.valueOf(userId), User.class);
        if (user == null) {
         //TODO throw exception
        }
        UUID uuid = UUID.randomUUID();
        logger.info("UUID: {}", uuid.toString());
        UserUploads userUploads = new UserUploads()
                .setUuid(uuid)
                .setFolder(formData.folder)
                .setUser(user)
                .setMimeType(formData.getMimeType())
                .setName(formData.fileName);

        userUploadsDAO.insert(userUploads);

        Path directory = volumeUtil.getActualPath(userId, formData.folder);
        volumeUtil.saveFile(uuid.toString(), directory, formData.file);

        return formData.getMimeType();
    }

    /**
     * Mark file unsafe and delete this in volume
     * @param id UserUploads id
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public void markUnsafe(Long id) {
        UserUploads userUpload = userUploadsDAO.findById(id, UserUploads.class);
        userUpload.setUnsafe(true)
                .setRemovedDate(LocalDateTime.now());

        Path path = volumeUtil.getActualPath(
                String.valueOf(
                        userUpload.getUser().getId()
                ),
                userUpload.getFolder(),
                userUpload.getUuid().toString()
        );
        CompletableFuture.runAsync(()->volumeUtil.deleteFile(path));
    }
}
