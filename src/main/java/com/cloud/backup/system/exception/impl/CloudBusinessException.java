package com.cloud.backup.system.exception.impl;

import jakarta.ws.rs.core.Response;

import java.io.Serial;
import java.io.Serializable;

public class CloudBusinessException extends RuntimeException implements Serializable {
    @Serial
    private static final long serialVersionUID = 6348520738936768701L;

    private Response.Status status;

    public CloudBusinessException() {
        super();
    }

    public CloudBusinessException(String message) {
        super(message);
    }

    public CloudBusinessException(String message, Response.Status status) {
        super(message);
        this.status = status;
    }

    public CloudBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public CloudBusinessException(Throwable cause) {
        super(cause);
    }

    public Response.Status getStatus(){
        return status;
    }
}
