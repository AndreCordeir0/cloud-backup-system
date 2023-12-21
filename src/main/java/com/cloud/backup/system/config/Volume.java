package com.cloud.backup.system.config;

import java.nio.file.Path;

public interface Volume {

    public Path getPath();

    public Path createFolder(String name);

    public void deleteFolder();

    public Path createStream(Long id, Path path);
}
