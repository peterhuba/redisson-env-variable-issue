package org.acme;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Provider
public class UnhandledExceptionMapper implements ExceptionMapper<RuntimeException> {

    @Override
    public Response toResponse(RuntimeException e) {
        log.error("Unhandled exception", e);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
            .build();
    }
}
