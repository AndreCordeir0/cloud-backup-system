package com.cloud.backup.system.config;

import java.nio.file.Path;

public interface Volume {

    public Path getPath();

    public Path createFolder(String name, String username);

    public void deleteFolder(Path path);

    public Path createStream(Long id, Path path);
}
