package org.acme.redis;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;

public class CacheConfiguration {

    public static final String DETAILS_CACHE_NAME = "detailsCache";

    @ApplicationScoped
    @Named(DETAILS_CACHE_NAME)
    RMapCache<String, String> buildCache(RedissonClient redissonClient) {
        return redissonClient.getMapCache(DETAILS_CACHE_NAME);
    }
}
