package org.acme.logging;

import static org.acme.logging.FilterHelper.OUTBOUND;
import static org.acme.logging.FilterHelper.createLogMessageJoiner;
import static org.acme.logging.FilterHelper.getHeadersAsString;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.StringJoiner;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;

import lombok.extern.slf4j.Slf4j;

@Provider
@Slf4j
public class ResponseLoggingFilter implements ContainerResponseFilter {

    @Context
    Providers providers;

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        StringJoiner logMessageJoiner = createLogMessageJoiner(OUTBOUND);
        logMessageJoiner.add("Status: " + responseContext.getStatus());
        logMessageJoiner.add("Headers: " + getHeadersAsString(responseContext.getHeaders()));
        logMessageJoiner.add("Body: " + getBody(responseContext));

        log.info(logMessageJoiner.toString());
    }

    private String getBody(ContainerResponseContext context) {
        if (!context.hasEntity()) {
            return "<no response body>";
        } else {
            return convertEntityToString(context);
        }
    }

    private String convertEntityToString(ContainerResponseContext context) {
        Class<?> entityClass = context.getEntityClass();
        Type entityType = context.getEntityType();
        Annotation[] entityAnnotations = context.getEntityAnnotations();
        MediaType mediaType = context.getMediaType();

        MessageBodyWriter<Object> messageBodyWriter = providers.getMessageBodyWriter((Class<Object>) entityClass, entityType,
            entityAnnotations, mediaType);

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            messageBodyWriter.writeTo(context.getEntity(), entityClass, entityType, entityAnnotations, mediaType, context.getHeaders(), outputStream);
            return new String(outputStream.toByteArray());
        } catch (IOException e) {
            log.warn("Couldn't log response body", e);
            return "<couldn't extract>";
        }
    }
}
