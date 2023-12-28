package com.cloud.backup.system.model.impl;

import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;
import org.apache.tika.Tika;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serial;
import java.io.Serializable;

public class FormData implements Serializable {

    @Serial
    private static final long serialVersionUID = 6354625779791548994L;

    @FormParam("file")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    @NotNull
    public byte[] file;

    @FormParam("name-file")
    @PartType(MediaType.TEXT_PLAIN)
    @NotNull
    public String fileName;

    @FormParam("folder")
    @PartType(MediaType.TEXT_PLAIN)
    public String folder;

    // Add a method to get the MIME type using Apache Tika
    public String getMimeType() {
        return new Tika().detect(file);
    }
}