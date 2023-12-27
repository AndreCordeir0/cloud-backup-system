package com.cloud.backup.system.config;

import java.nio.file.Path;

public interface Volume {

    public Path getPath();

    public void createFolder(String name, String userId);

    public void deleteFolder(Path path);

    public Path createStream(Long id, Path path);
}
