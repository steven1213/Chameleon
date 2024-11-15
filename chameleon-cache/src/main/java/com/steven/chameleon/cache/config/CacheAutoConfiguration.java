package com.steven.chameleon.cache.config;

import com.steven.chameleon.cache.annotation.Cache;
import com.steven.chameleon.cache.annotation.CacheEvict;
import com.steven.chameleon.cache.annotation.CachePut;
import com.steven.chameleon.cache.core.CacheManager;
import com.steven.chameleon.cache.core.MemoryCacheManager;
import com.steven.chameleon.cache.interceptor.CacheInterceptor;
import com.steven.chameleon.cache.support.KeyGenerator;
import com.steven.chameleon.cache.support.SimpleKeyGenerator;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 缓存自动配置类
 */
@Configuration
@EnableConfigurationProperties(CacheProperties.class)
@ConditionalOnProperty(prefix = "chameleon.cache", name = "enabled", havingValue = "true", matchIfMissing = true)
public class CacheAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public KeyGenerator keyGenerator() {
        return new SimpleKeyGenerator();
    }

    @Bean
    @ConditionalOnMissingBean
    public CacheManager cacheManager(CacheProperties properties) {
        return new MemoryCacheManager();
    }

    @Bean
    public CacheInterceptor cacheInterceptor(CacheManager cacheManager) {
        return new CacheInterceptor(cacheManager);
    }

    @Bean
    public Advisor cacheAdvisor(CacheInterceptor cacheInterceptor) {
        Pointcut cachePointcut = new AnnotationMatchingPointcut(null, Cache.class);
        Pointcut putPointcut = new AnnotationMatchingPointcut(null, CachePut.class);
        Pointcut evictPointcut = new AnnotationMatchingPointcut(null, CacheEvict.class);
        
        ComposablePointcut pointcut = new ComposablePointcut(cachePointcut);
        pointcut.union(putPointcut).union(evictPointcut);
        return new DefaultPointcutAdvisor(pointcut, cacheInterceptor);
    }
}