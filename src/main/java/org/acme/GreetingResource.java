package org.acme;

import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.acme.redis.CacheService;

@Path("/hello")
public class GreetingResource {

    @Inject
    CacheService cacheService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        String uuid = UUID.randomUUID().toString();
        cacheService.addDetails(uuid, "testdata");
        return uuid;
    }
}