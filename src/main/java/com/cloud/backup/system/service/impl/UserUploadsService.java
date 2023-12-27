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

import java.util.UUID;

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
    public String saveStream(@MultipartForm FormData formData, Long userId) {
        User user = userDAO.findById(userId, User.class);
        if (user == null) {
         //TODO throw exception
        }
        UUID uuid = UUID.randomUUID();
        logger.info("UUID: {}", uuid.toString());
        UserUploads userUploads = new UserUploads();
        userUploads.setUuid(uuid);
        userUploads.setFolder(formData.folder);
        userUploads.setUser(user);
        userUploads.setMimeType(formData.getMimeType());
        //TODO save the file in directory
        //volumeUtil.createStream();

        userUploadsDAO.insert(userUploads);

        return formData.getMimeType();
    }
}
