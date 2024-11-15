package com.steven.chameleon.core.cache;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * <template>desc</template>.
 *
 * @author: steven
 * @date: 2024/11/15 10:52
 */
public interface Cache<K, V> {
    /**
     * 获取缓存
     */
    V get(K key);

    /**
     * 获取缓存，不存在则计算
     */
    V get(K key, Callable<V> loader);

    /**
     * 放入缓存
     */
    void put(K key, V value);

    /**
     * 放入缓存并设置过期时间
     */
    void put(K key, V value, long timeout, TimeUnit unit);

    /**
     * 删除缓存
     */
    void remove(K key);

    /**
     * 清空缓存
     */
    void clear();

    /**
     * 获取缓存大小
     */
    int size();
}
