package org.acme.logging;

import static org.acme.logging.FilterHelper.OUTBOUND;
import static org.acme.logging.FilterHelper.createLogMessageJoiner;
import static org.acme.logging.FilterHelper.getHeadersAsString;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

import lombok.extern.slf4j.Slf4j;

@Provider
@Slf4j
@PreMatching
public class ClientResponseLoggingFilter implements ClientResponseFilter {

    @Override
    public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) {
        StringJoiner logMessageJoiner = createLogMessageJoiner(OUTBOUND);
        logMessageJoiner.add("Status: " + responseContext.getStatus());
        logMessageJoiner.add("Headers: " + getHeadersAsString(responseContext.getHeaders()));
        logMessageJoiner.add("Body: " + getBody(responseContext));

        log.info(logMessageJoiner.toString());
    }

    private String getBody(ClientResponseContext context) {
        if (!context.hasEntity()) {
            return "<no response body>";
        } else {
            return convertEntityToString(context);
        }
    }

    private String convertEntityToString(ClientResponseContext context) {
        InputStream entityStream = context.getEntityStream();

        try {
            return new String(entityStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.warn("Couldn't log response body", e);
            return "<couldn't extract>";
        }
    }
}