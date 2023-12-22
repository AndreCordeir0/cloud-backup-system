package com.cloud.backup.system.exception.impl;

import jakarta.ws.rs.core.Response;

public class ErrorMessage {

    private String message;
    private Response.Status status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Response.Status getStatus() {
        return status;
    }

    public void setStatus(Response.Status status) {
        this.status = status;
    }

    public ErrorMessage(String message, Response.Status status) {
        super();
        this.message = message;
        this.status = status;
    }

    public ErrorMessage() {
        super();
    }

}
