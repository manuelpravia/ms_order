package org.mpravia.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@ApplicationScoped
public class CacheServiceImpl {

    @Inject
    RedissonClient redissonClient;

    public <K, V> V getOrLoad(
            String mapName,
            K key,
            long ttlSeconds,
            Supplier<V> loader) {

        RMapCache<K, V> map = redissonClient.getMapCache(mapName);

        V value = map.get(key);

        if (value != null) {
            return value;
        }

        value = loader.get();

        if (value != null) {
            map.put(key, value, ttlSeconds, TimeUnit.SECONDS);
        }

        return value;
    }

    public <K, V> V get(String mapName, K key) {

        RMapCache<K, V> map = redissonClient.getMapCache(mapName);

        return map.get(key);
    }

    public <K,V> void put(String mapName, K key, V value, long ttl) {

        redissonClient
                .getMapCache(mapName)
                .put(key,value,ttl,TimeUnit.SECONDS);
    }

    public <K> void evict(String mapName, K key){

        redissonClient
                .getMapCache(mapName)
                .remove(key);
    }

}