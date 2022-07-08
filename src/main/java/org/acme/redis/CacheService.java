package org.acme.redis;

import static org.acme.redis.CacheConfiguration.DETAILS_CACHE_NAME;

import java.util.concurrent.TimeUnit;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.redisson.api.RMapCache;

@ApplicationScoped
public class CacheService {

    private static final int ENTRY_EXPIRATION = 30;

    @Inject
    @Named(DETAILS_CACHE_NAME)
    RMapCache<String, String> detailsCache;

    public void addDetails(String uuid, String details) {
        detailsCache.put(uuid, details, ENTRY_EXPIRATION, TimeUnit.MINUTES);
    }
}
