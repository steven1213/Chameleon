package com.steven.chameleon.cache.core;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 基于内存的缓存管理器实现
 */
public class MemoryCacheManager implements CacheManager {
    private final ConcurrentMap<String, Cache<?, ?>> cacheMap = new ConcurrentHashMap<>();

    @Override
    public Cache<?, ?> getCache(String name) {
        return cacheMap.get(name);
    }

    @Override
    public Collection<String> getCacheNames() {
        return cacheMap.keySet();
    }

    @Override
    public Cache<?, ?> createCache(String name) {
        return cacheMap.computeIfAbsent(name, k -> new MemoryCache<>(name));
    }

    @Override
    public void removeCache(String name) {
        Cache<?, ?> cache = cacheMap.remove(name);
        if (cache != null) {
            cache.clear();
        }
    }
}