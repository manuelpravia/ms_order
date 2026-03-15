package org.mpravia.service;

import java.util.function.Supplier;

public interface CacheService {

    <K, V> V getOrLoad(String mapName, K key, long ttlSeconds, Supplier<V> loader);

    <K, V> V get(String mapName, K key);

    <K,V> void put(String mapName, K key, V value, long ttl);

    <K> void evict(String mapName, K key);

}
