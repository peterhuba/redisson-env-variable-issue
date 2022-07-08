package org.acme.logging;

import static org.acme.logging.FilterHelper.INBOUND;
import static org.acme.logging.FilterHelper.createLogMessageJoiner;
import static org.acme.logging.FilterHelper.getHeadersAsString;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.StringJoiner;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.io.IOUtils;

import io.smallrye.common.annotation.Blocking;
import io.vertx.core.http.HttpServerRequest;
import lombok.extern.slf4j.Slf4j;

@Provider
@Slf4j
@PreMatching
public class RequestLoggingFilter implements ContainerRequestFilter {

    @Context
    HttpServerRequest request;

    @Override
    public void filter(ContainerRequestContext context) {
        StringJoiner logMessageJoiner = createLogMessageJoiner(INBOUND);
        logMessageJoiner.add("Method: " + context.getMethod() + ", Path: " + context.getUriInfo().getPath());
        logMessageJoiner.add("Headers: " + getHeadersAsString(context.getHeaders()));
//        logMessageJoiner.add("Body: " + getBody(context));

        log.info(logMessageJoiner.toString());
    }

//    private String getBody(ContainerRequestContext context) {
//        try {
//            String body = IOUtils.toString(context.getEntityStream(), Charset.defaultCharset());
//            InputStream copiedInputStream = IOUtils.toInputStream(body, Charset.defaultCharset());
//            context.setEntityStream(copiedInputStream);
//
//            return StringUtils.isEmpty(body) ? "<no request body>" : body;
//        } catch (IOException e) {
//            log.warn("Couldn't log request body", e);
//            return "<couldn't extract>";
//        }
//    }
}
