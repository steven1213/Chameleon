package com.steven.chameleon.cache.support;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 缓存操作的基础类，封装缓存操作的通用属性
 */
public class CacheOperation {
    private final String name;
    private final String key;
    private final Method method;
    private final String condition;
    private final long expire;
    private final OperationType operationType;

    public enum OperationType {
        CACHE,      // @Cache
        EVICT,      // @CacheEvict
        PUT         // @CachePut
    }

    private CacheOperation(Builder builder) {
        this.name = builder.name;
        this.key = builder.key;
        this.method = builder.method;
        this.condition = builder.condition;
        this.expire = builder.expire;
        this.operationType = builder.operationType;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public Method getMethod() {
        return method;
    }

    public String getCondition() {
        return condition;
    }

    public long getExpire() {
        return expire;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public static class Builder {
        private String name;
        private String key;
        private Method method;
        private String condition;
        private long expire;
        private OperationType operationType;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setKey(String key) {
            this.key = key;
            return this;
        }

        public Builder setMethod(Method method) {
            this.method = method;
            return this;
        }

        public Builder setCondition(String condition) {
            this.condition = condition;
            return this;
        }

        public Builder setExpire(long expire) {
            this.expire = expire;
            return this;
        }

        public Builder setOperationType(OperationType operationType) {
            this.operationType = operationType;
            return this;
        }

        public CacheOperation build() {
            return new CacheOperation(this);
        }
    }
}
