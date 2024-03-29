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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
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


    private final static int TWO_HUNDRED_MB_IN_KB = 200000;

    public String createFolder(String folderName, String userId) {
        if (folderName == null) {
            throw new CloudBusinessException("The folder name cannot be null.", Status.BAD_REQUEST);
        }
        volumeUtil.createFolder(folderName, userId);
        return "Folder created";
    }
    //TODO Compression file
    @Transactional(Transactional.TxType.REQUIRED)
    public String saveFile(@MultipartForm FormData formData, String userId) {
        User user = userDAO.findById(Long.valueOf(userId), User.class);
        if (user == null) {
            //TODO throw exception
        }
        var sizeInKB = formData.file.length / 1024;
        logger.info("Tamanho: {}", sizeInKB);
        if (sizeInKB > TWO_HUNDRED_MB_IN_KB) {
            throw new CloudBusinessException("Maximum file size is 200MB", Status.NOT_ACCEPTABLE);
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
     *
     * @param id UserUploads id
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public void markUnsafe(Long id) {
        UserUploads userUpload = userUploadsDAO.findById(id, UserUploads.class);
        userUpload.setUnsafe(true)
                .setRemovedDate(LocalDateTime.now());

        Path path = mountPathUsingUserUploads(userUpload);
        CompletableFuture
                .runAsync(() ->
                        volumeUtil.deleteFile(path)
                ).exceptionally((ex) -> {
                    userUpload.setRemovedDate(null);
                    logger.error("Erro ao deletar arquivo : {}", ex.getMessage());
                    return null;
                });
    }
    @Transactional
    public void deleteFilesMarked() {
        List<UserUploads> uploads = userUploadsDAO.getFilesMarkedAndNotDeleted();
        for (UserUploads upload : uploads) {
            Path path = mountPathUsingUserUploads(upload);
            upload.setRemovedDate(LocalDateTime.now());
            volumeUtil.deleteFile(path);
        }
    }

    public InputStream downloadFile(UUID uuid, String userId) {
        UserUploads upload = userUploadsDAO.getFileFromUUID(uuid, Long.valueOf(userId));
        if (upload == null) {
            throw new CloudBusinessException("File not found.", Status.NOT_FOUND);
        }
        byte[] byteFile = volumeUtil.getFile(mountPathUsingUserUploads(upload));
        return new ByteArrayInputStream(byteFile);
    }

    private Path mountPathUsingUserUploads(UserUploads userUpload) {
        return volumeUtil.getActualPath(
                String.valueOf(
                        userUpload.getUser().getId()
                ),
                userUpload.getFolder(),
                userUpload.getUuid().toString()
        );
    }
}
