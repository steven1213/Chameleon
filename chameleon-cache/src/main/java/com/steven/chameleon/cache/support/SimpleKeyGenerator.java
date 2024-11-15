package com.steven.chameleon.cache.support;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 默认的键生成器实现
 */
public class SimpleKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        return generateKey(params);
    }

    /**
     * 生成键的具体实现
     */
    private Object generateKey(Object... params) {
        if (params.length == 0) {
            return SimpleKey.EMPTY;
        }
        if (params.length == 1) {
            Object param = params[0];
            if (param != null && !param.getClass().isArray()) {
                return param;
            }
        }
        return new SimpleKey(params);
    }

    /**
     * 简单键实现
     */
    static class SimpleKey {
        public static final SimpleKey EMPTY = new SimpleKey();

        private final Object[] params;
        private final int hash;

        public SimpleKey(Object... elements) {
            this.params = elements;
            this.hash = Arrays.deepHashCode(elements);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SimpleKey)) {
                return false;
            }
            SimpleKey other = (SimpleKey) obj;
            return Arrays.deepEquals(this.params, other.params);
        }

        @Override
        public int hashCode() {
            return this.hash;
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + " [" + Arrays.deepToString(this.params) + "]";
        }
    }
}
