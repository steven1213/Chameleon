package com.steven.chameleon.cache.support;

import java.lang.reflect.Method;

/**
 * 缓存键生成器接口
 */
public interface KeyGenerator {
    /**
     * 生成缓存键
     * @param target 目标对象
     * @param method 方法
     * @param params 参数
     * @return 生成的键
     */
    Object generate(Object target, Method method, Object... params);
}