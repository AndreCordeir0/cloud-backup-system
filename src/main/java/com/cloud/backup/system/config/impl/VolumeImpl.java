package com.cloud.backup.system.config.impl;

import com.cloud.backup.system.config.Volume;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@ApplicationScoped
public class VolumeImpl implements Volume {
    private static final Logger logger = LoggerFactory.getLogger(VolumeImpl.class);

    @ConfigProperty(name = "volume.path")
    String volumePath;

    @Override
    public Path getPath() {
        return Path.of(URI.create(volumePath));
    }

    @Override
    public void createFolder(String pathName, String userId) {
        String concatPath = pathConcat(userId,pathName);
        Path directory = Paths.get(concatPath);
        try {
            Files.createDirectories(directory);
        }catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteFolder(Path path) {
        try {
            boolean deleted = Files.deleteIfExists(path);
            if (deleted) {
                logger.info("Folder deleted successful");
            } else {
                logger.info("Folder deleted successful");
            }
        }catch (IOException e) {
            logger.error("Error deleting the folder in path: {}", path.toString());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Path createStream(Long id, Path path) {
        return null;
    }

    private String pathConcat(String ...args) {
        StringBuilder sb = new StringBuilder();
        sb.append(volumePath);
        for (String path : args) {
            sb.append("/");
            sb.append(replaceSpacesWithUnderlining(path));
        }
        return sb.toString();
    }

    private String replaceSpacesWithUnderlining(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Objects.equals(c, ' ')) {
                sb.append("_");
            }else {
                sb.append(c);
            }
        }
        return sb.toString();
    }


}
