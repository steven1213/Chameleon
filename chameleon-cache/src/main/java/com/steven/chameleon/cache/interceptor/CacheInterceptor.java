package com.steven.chameleon.cache.interceptor;

import com.steven.chameleon.cache.annotation.Cache;
import com.steven.chameleon.cache.annotation.CacheEvict;
import com.steven.chameleon.cache.annotation.CachePut;
import com.steven.chameleon.cache.core.CacheManager;
import com.steven.chameleon.cache.support.CacheExpressionEvaluator;
import com.steven.chameleon.cache.support.CacheOperation;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * 缓存拦截器，处理缓存注解的核心逻辑
 */
public class CacheInterceptor implements MethodInterceptor {

    private final CacheManager cacheManager;
    private final CacheExpressionEvaluator evaluator;

    public CacheInterceptor(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
        this.evaluator = new CacheExpressionEvaluator();
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        Object[] args = invocation.getArguments();

        // 获取方法上的所有缓存操作
        List<CacheOperation> operations = getCacheOperations(method);
        if (operations.isEmpty()) {
            return invocation.proceed();
        }

        // 处理缓存操作
        return execute(invocation, operations);
    }

    private Object execute(MethodInvocation invocation, List<CacheOperation> operations) throws Throwable {
        // 处理@Cache注解
        Object result = processCacheableOperation(invocation, operations);
        if (result != null) {
            return result;
        }

        // 执行实际方法
        result = invocation.proceed();

        // 处理@CachePut注解
        processCachePutOperation(invocation, operations, result);

        // 处理@CacheEvict注解
        processCacheEvictOperation(invocation, operations);

        return result;
    }

    private Object processCacheableOperation(MethodInvocation invocation, List<CacheOperation> operations) {
        for (CacheOperation operation : operations) {
            if (operation.getOperationType() != CacheOperation.OperationType.CACHE) {
                continue;
            }

            // 检查条件表达式
            if (!evaluator.parseCondition(operation.getCondition(), invocation.getMethod(), invocation.getArguments())) {
                continue;
            }

            // 生成缓存键
            Object key = evaluator.parseKey(operation.getKey(), invocation.getMethod(), invocation.getArguments());

            // 获取缓存
            com.steven.chameleon.cache.core.Cache<Object, Object> cache =
                    (com.steven.chameleon.cache.core.Cache<Object, Object>) cacheManager.getCache(operation.getName());

            if (cache != null) {
                return cache.get(key, new Callable<Object>() {
                    @Override
                    public Object call() throws Exception {
                        try {
                            return invocation.proceed();
                        } catch (Throwable e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        }
        return null;
    }

    private void processCachePutOperation(MethodInvocation invocation, List<CacheOperation> operations, Object result) {
        for (CacheOperation operation : operations) {
            if (operation.getOperationType() != CacheOperation.OperationType.PUT) {
                continue;
            }

            // 检查条件表达式
            if (!evaluator.parseCondition(operation.getCondition(), invocation.getMethod(), invocation.getArguments())) {
                continue;
            }

            // 生成缓存键
            Object key = evaluator.parseKey(operation.getKey(), invocation.getMethod(), invocation.getArguments());

            // 更新缓存
            com.steven.chameleon.cache.core.Cache<Object, Object> cache =
                    (com.steven.chameleon.cache.core.Cache<Object, Object>) cacheManager.getCache(operation.getName());

            if (cache != null) {
                cache.put(key, result, operation.getExpire());
            }
        }
    }

    private void processCacheEvictOperation(MethodInvocation invocation, List<CacheOperation> operations) {
        for (CacheOperation operation : operations) {
            if (operation.getOperationType() != CacheOperation.OperationType.EVICT) {
                continue;
            }

            // 检查条件表达式
            if (!evaluator.parseCondition(operation.getCondition(), invocation.getMethod(), invocation.getArguments())) {
                continue;
            }

            // 获取缓存
            com.steven.chameleon.cache.core.Cache<Object, Object> cache =
                    (com.steven.chameleon.cache.core.Cache<Object, Object>) cacheManager.getCache(operation.getName());

            if (cache != null) {
                // 生成缓存键
                Object key = evaluator.parseKey(operation.getKey(), invocation.getMethod(), invocation.getArguments());
                cache.evict(key);
            }
        }
    }

    private List<CacheOperation> getCacheOperations(Method method) {
        List<CacheOperation> operations = new ArrayList<>();

        // 处理@Cache注解
        Cache cache = method.getAnnotation(Cache.class);
        if (cache != null) {
            operations.add(new CacheOperation.Builder()
                    .setName(cache.name())
                    .setKey(cache.key())
                    .setMethod(method)
                    .setCondition(cache.condition())
                    .setExpire(cache.expire())
                    .setOperationType(CacheOperation.OperationType.CACHE)
                    .build());
        }

        // 处理@CachePut注解
        CachePut cachePut = method.getAnnotation(CachePut.class);
        if (cachePut != null) {
            operations.add(new CacheOperation.Builder()
                    .setName(cachePut.name())
                    .setKey(cachePut.key())
                    .setMethod(method)
                    .setCondition(cachePut.condition())
                    .setExpire(cachePut.expire())
                    .setOperationType(CacheOperation.OperationType.PUT)
                    .build());
        }

        // 处理@CacheEvict注解
        CacheEvict cacheEvict = method.getAnnotation(CacheEvict.class);
        if (cacheEvict != null) {
            operations.add(new CacheOperation.Builder()
                    .setName(cacheEvict.name())
                    .setKey(cacheEvict.key())
                    .setMethod(method)
                    .setCondition(cacheEvict.condition())
                    .setOperationType(CacheOperation.OperationType.EVICT)
                    .build());
        }

        return operations;
    }
}
