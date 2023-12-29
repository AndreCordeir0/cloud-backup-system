package com.cloud.backup.system.config;

import java.nio.file.Path;

public interface Volume {

    public Path getActualPath(String ...args);

    public void createFolder(String name, String userId);

    public void deleteFolder(Path path);

    public void saveFile(String hash, Path path, byte[] content);

    public void deleteFile(Path path);
}
