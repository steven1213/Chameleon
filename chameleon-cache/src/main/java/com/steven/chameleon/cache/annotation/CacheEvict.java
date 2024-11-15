package com.steven.chameleon.cache.annotation;

import java.lang.annotation.*;

/**
 * <template>desc</template>.
 *
 * @author: steven
 * @date: 2024/11/15 15:01
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface CacheEvict {
    /**
     * 缓存名称
     */
    String name() default "";

    /**
     * 缓存键，支持 SpEL 表达式
     */
    String key() default "";

    /**
     * 是否清除所有缓存（默认只清除指定key的缓存）
     */
    boolean allEntries() default false;

    /**
     * 是否在方法执行前清除缓存
     */
    boolean beforeInvocation() default false;

    /**
     * 条件表达式，支持 SpEL
     */
    String condition() default "";
}
