package com.steven.chameleon.core.config;

/**
 * <template>desc</template>.
 *
 * @author: steven
 * @date: 2024/11/14 16:54
 */
public interface PoolConfig {
    /**
     * 获取最小连接数
     */
    int getMinPoolSize();

    /**
     * 获取最大连接数
     */
    int getMaxPoolSize();

    /**
     * 获取连接超时时间
     */
    int getConnectionTimeout();

    /**
     * 获取空闲超时时间
     */
    long getIdleTimeout();
}
