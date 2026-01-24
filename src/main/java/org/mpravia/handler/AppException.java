package org.mpravia.handler;

import jakarta.ws.rs.core.Response.Status;
import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
    private final String errorCode;
    private final Status status;

    public AppException(String errorCode, String message, Status status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }

    public int getStatusCode() { return status.getStatusCode(); }
}
