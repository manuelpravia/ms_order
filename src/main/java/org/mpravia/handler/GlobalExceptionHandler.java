package org.mpravia.handler;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.time.LocalDateTime;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Throwable> {

    @Inject
    Logger logger;

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(Throwable exception) {

        if (exception instanceof AppException appEx) {
            return buildResponse(appEx.getErrorCode(), appEx.getMessage(),
                    appEx.getStatusCode());
        }

        logger.error("Error no manejado en la aplicación: ", exception);
        return buildResponse("INTERNAL_SERVER_ERROR",
                "Ocurrió un error inesperado en el servidor.",
                500);
    }

    private Response buildResponse(String code, String message, int status) {
        ErrorDetails error = new ErrorDetails(
                code,
                message,
                status,
                LocalDateTime.now(),
                uriInfo.getPath()
        );

        return Response.status(status)
                .entity(error)
                .build();
    }

}
