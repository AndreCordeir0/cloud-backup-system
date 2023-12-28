package com.cloud.backup.system.config.impl;

import com.cloud.backup.system.config.Volume;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
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
        Path directory = getActualPath(userId,pathName);
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
    public void saveFile(String hash, Path path, byte[] content) {
        try {
            File file = new File(path.toString() + "/" + hash);
            Files.write(file.toPath(), content);
        }catch (IOException e) {
            logger.error("error in create file: {}", path.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * don't concat if args is null
     * @param args
     * @return
     */
    private String pathConcat(String ...args) {
        StringBuilder sb = new StringBuilder();
        sb.append(volumePath);
        for (String path : args) {
            if (path != null) {
                sb.append("/");
                sb.append(replaceSpacesWithUnderlining(path));
            }
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

    public Path getActualPath(String ...args) {
        String concatPath = pathConcat(args);
        Path path = Paths.get(concatPath);
        try {
            Files.createDirectories(path);
        }catch (IOException e){
            logger.error("Error creating directory");
            throw new RuntimeException(e);
        }
        return path;
    }
}
