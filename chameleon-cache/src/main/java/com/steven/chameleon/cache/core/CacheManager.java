package com.steven.chameleon.cache.core;

import java.util.Collection;

/**
 * 缓存管理器，负责缓存的创建和管理
 */
public interface CacheManager {
    /**
     * 根据名称获取缓存
     */
    Cache<?, ?> getCache(String name);

    /**
     * 获取所有缓存名称
     */
    Collection<String> getCacheNames();

    /**
     * 创建缓存
     */
    Cache<?, ?> createCache(String name);

    /**
     * 移除缓存
     */
    void removeCache(String name);
}