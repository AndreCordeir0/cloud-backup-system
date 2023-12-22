package com.cloud.backup.system.exception.handler;

import com.cloud.backup.system.exception.impl.CloudBusinessException;
import com.cloud.backup.system.exception.impl.ErrorMessage;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class ExceptionHandler implements ExceptionMapper<CloudBusinessException> {

    @Override
    public Response toResponse(CloudBusinessException e) {
        return Response.status(e.getStatus()).
                entity(new ErrorMessage(e.getMessage(), e.getStatus())).build();
    }
}
