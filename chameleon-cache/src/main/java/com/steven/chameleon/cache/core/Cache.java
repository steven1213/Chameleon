package com.steven.chameleon.cache.core;

import java.util.concurrent.Callable;

/**
 * 缓存核心接口，定义基本的缓存操作
 * @param <K> 键的类型
 * @param <V> 值的类型
 */
public interface Cache<K, V> {
    /**
     * 获取缓存名称
     */
    String getName();

    /**
     * 获取实际的缓存对象
     */
    Object getNativeCache();

    /**
     * 根据键获取缓存值
     */
    V get(K key);

    /**
     * 获取缓存，如果不存在则通过 valueLoader 加载
     */
    V get(K key, Callable<V> valueLoader);

    /**
     * 放入缓存
     */
    void put(K key, V value);

    /**
     * 放入缓存并设置过期时间（秒）
     */
    void put(K key, V value, long expire);

    /**
     * 删除缓存
     */
    void evict(K key);

    /**
     * 清空所有缓存
     */
    void clear();
}