package com.steven.chameleon.cache.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * 缓存配置属性
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "chameleon.cache")
public class CacheProperties {
    /**
     * 是否启用缓存
     */
    private boolean enabled = true;

    /**
     * 默认过期时间（秒）
     */
    private long defaultExpiration = 0;

    /**
     * 是否允许空值
     */
    private boolean allowNullValues = true;

    /**
     * 缓存类型：memory, redis 等
     */
    private String type = "memory";

    /**
     * 每个缓存的具体配置
     */
    private Map<String, CacheConfig> caches = new HashMap<>();


    /**
     * 单个缓存配置
     */
    @Getter
    @Setter
    public static class CacheConfig {
        /**
         * 过期时间（秒）
         */
        private long expiration = 0;

        /**
         * 最大容量
         */
        private int maxSize = -1;
    }
}
