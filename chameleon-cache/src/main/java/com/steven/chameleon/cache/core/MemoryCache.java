package com.steven.chameleon.cache.core;

import java.util.Map;
import java.util.concurrent.*;

/**
 * 基于内存的缓存实现
 */
public class MemoryCache<K, V> implements Cache<K, V> {
    private final String name;
    private final Map<K, ValueWrapper> store;
    private final ScheduledExecutorService cleanupExecutor;

    public MemoryCache(String name) {
        this.name = name;
        this.store = new ConcurrentHashMap<>();
        this.cleanupExecutor = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getNativeCache() {
        return this.store;
    }

    @Override
    public V get(K key) {
        ValueWrapper wrapper = store.get(key);
        if (wrapper == null || wrapper.isExpired()) {
            store.remove(key);
            return null;
        }
        return wrapper.value;
    }

    @Override
    public V get(K key, Callable<V> valueLoader) {
        V value = get(key);
        if (value != null) {
            return value;
        }

        try {
            value = valueLoader.call();
            put(key, value);
            return value;
        } catch (Exception e) {
            throw new RuntimeException("Error loading value", e);
        }
    }

    @Override
    public void put(K key, V value) {
        put(key, value, 0);
    }

    @Override
    public void put(K key, V value, long expire) {
        ValueWrapper wrapper = new ValueWrapper(value, expire);
        store.put(key, wrapper);

        if (expire > 0) {
            cleanupExecutor.schedule(() -> store.remove(key), expire, TimeUnit.SECONDS);
        }
    }

    @Override
    public void evict(K key) {
        store.remove(key);
    }

    @Override
    public void clear() {
        store.clear();
    }

    private class ValueWrapper {
        final V value;
        final long expireTime;

        ValueWrapper(V value, long expire) {
            this.value = value;
            this.expireTime = expire > 0 ? System.currentTimeMillis() + (expire * 1000) : 0;
        }

        boolean isExpired() {
            return expireTime > 0 && System.currentTimeMillis() > expireTime;
        }
    }
}
