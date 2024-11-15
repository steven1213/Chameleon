package com.steven.chameleon.cache.annotation;

import java.lang.annotation.*;

/**
 * <template>desc</template>.
 *
 * @author: steven
 * @date: 2024/11/15 15:00
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Cache {
    /**
     * 缓存名称
     */
    String name() default "";

    /**
     * 缓存键，支持 SpEL 表达式
     */
    String key() default "";

    /**
     * 过期时间（秒）
     */
    long expire() default 0L;

    /**
     * 是否使用前缀
     */
    boolean usePrefix() default true;

    /**
     * 是否同步加载缓存
     */
    boolean sync() default false;

    /**
     * 条件表达式，支持 SpEL
     */
    String condition() default "";

    /**
     * 除非表达式，支持 SpEL
     */
    String unless() default "";
}
