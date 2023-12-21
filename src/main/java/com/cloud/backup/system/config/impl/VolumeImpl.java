package com.cloud.backup.system.config.impl;

import com.cloud.backup.system.config.Volume;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.net.URI;
import java.nio.file.Path;

public class VolumeImpl implements Volume {

    @ConfigProperty(name = "volume.path")
    String volumePath;


    @Override
    public Path getPath() {
        return Path.of(URI.create(volumePath));
    }

    @Override
    public Path createFolder(String name) {
        return null;
    }

    @Override
    public void deleteFolder() {

    }

    @Override
    public Path createStream(Long id, Path path) {
        return null;
    }
}
