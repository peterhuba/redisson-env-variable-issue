package org.acme.logging;

import static org.acme.logging.FilterHelper.INBOUND;
import static org.acme.logging.FilterHelper.createLogMessageJoiner;
import static org.acme.logging.FilterHelper.getHeadersAsString;

import java.util.StringJoiner;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

import lombok.extern.slf4j.Slf4j;

@Provider
@Slf4j
@PreMatching
public class ClientRequestLoggingFilter implements ClientRequestFilter {

    @Override
    public void filter(ClientRequestContext context) {
        StringJoiner logMessageJoiner = createLogMessageJoiner(INBOUND);
        logMessageJoiner.add("Method: " + context.getMethod() + ", Path: " + context.getUri().getPath());
        logMessageJoiner.add("Headers: " + getHeadersAsString(context.getHeaders()));
        logMessageJoiner.add("Body: " + context.getEntity());

        log.info(logMessageJoiner.toString());
    }
}