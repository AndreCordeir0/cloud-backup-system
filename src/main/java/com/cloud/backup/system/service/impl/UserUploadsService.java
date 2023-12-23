package com.cloud.backup.system.service.impl;

import com.cloud.backup.system.config.impl.VolumeImpl;
import com.cloud.backup.system.exception.impl.CloudBusinessException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import static jakarta.ws.rs.core.Response.Status;

@ApplicationScoped
public class UserUploadsService {

    @Inject
    VolumeImpl volumeUtil;

    public String createFolder(String folderName, String userId) {
        if (folderName == null) {
            throw new CloudBusinessException("The folder name cannot be null.", Status.BAD_REQUEST);
        }
        volumeUtil.createFolder(folderName, userId);
        return "Folder created";
    }
}
